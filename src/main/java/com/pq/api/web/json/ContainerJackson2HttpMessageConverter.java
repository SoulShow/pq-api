package com.pq.api.web.json;

import com.pq.api.exception.AppErrorCode;
import com.pq.api.utils.PrintableInputStream;
import com.pq.api.utils.PrintableOutputStream;
import com.pq.api.utils.Result;
import com.pq.api.web.context.Client;
import com.pq.api.web.context.ClientContextHolder;
import com.pq.api.web.context.Invisiable;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;

/**
 * 针对Jackson2的 Message Converter，改变输出的结果集，让结果集在统一的容器中
 *
 * @author liken
 * @date 15/3/14
 */
@Component
public class ContainerJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    private boolean ignoreInvisiable = true;//默认是忽略

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 是否设置 忽略标记 隐身的类，如果忽略，表示我要查看所有日志
     * 如果不忽略，表示隐私控制生效，生产环境，一般是不忽略
     *
     * @param ignoreInvisiable
     */
    public void setIgnoreInvisiable(boolean ignoreInvisiable) {
        this.ignoreInvisiable = ignoreInvisiable;
    }

    //目前通过这个方法，读取 Content-Type: application/json 的上传信息
    @Override
    public Object read(Type type, Class<?> contextClass,
                       HttpInputMessage inputMessage) throws IOException,
            HttpMessageNotReadableException {
        //只有不是隐身类，才会被打印日志
        if (!(inputMessage instanceof HttpInputMessageWrapper) && !isIgnoreInvisiable(contextClass)) {
            final PrintableInputStream printableInputStream = new PrintableInputStream(inputMessage.getBody());

            HttpInputMessage httpInputMessageWrapper = new HttpInputMessageWrapper(inputMessage, printableInputStream);

            Object result = null;
            try {
                result = super.read(type, contextClass, httpInputMessageWrapper);
            } finally {
                Client client = ClientContextHolder.getClient();
                String requestBody = printableInputStream.toString();
                if (!ignoreInvisiable) {
                    if (StringUtils.contains(requestBody, "assword")) {
                        requestBody = "*** protected ***";
                    }
                }
                client.getRequestContext().setRequestBody(requestBody);
                client.getRequestContext().setRequestBodyLength(printableInputStream.getLength());
            }
            return result;
        }

        return super.read(type, contextClass, inputMessage);
    }

    @Override
    protected Object readInternal(Class<?> clazz, final HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        //只有不是隐身类，才会被打印日志
        if (!(inputMessage instanceof HttpInputMessageWrapper) && !isIgnoreInvisiable(clazz)) {
            final PrintableInputStream printableInputStream = new PrintableInputStream(inputMessage.getBody());

            HttpInputMessage httpInputMessageWrapper = new HttpInputMessageWrapper(inputMessage, printableInputStream);

            Object result = null;
            try {
                result = super.readInternal(clazz, httpInputMessageWrapper);
            } finally {
                Client client = ClientContextHolder.getClient();

                String requestBody = printableInputStream.toString();
                if (!ignoreInvisiable) {
                    if (StringUtils.contains(requestBody, "assword")) {
                        requestBody = "*** protected ***";
                    }
                }

                client.getRequestContext().setRequestBody(requestBody);
                client.getRequestContext().setRequestBodyLength(printableInputStream.getLength());
            }
            return result;
        }

        return super.readInternal(clazz, inputMessage);
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {

        //只要是跟 swagger ui 有关系的，都不支持
        if (StringUtils.contains(clazz.getPackage().getName(), "swagger")) {
            return false;
        }

//        logger.info("class is: {}, mediaType: {}, can ? {}", clazz, mediaType, can);
        return super.canRead(clazz, mediaType);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {

        //只要是跟 swagger ui 有关系的，都不支持
        if (StringUtils.contains(clazz.getPackage().getName(), "swagger")) {
            return false;
        }

        return super.canWrite(clazz, mediaType);
    }

    @Override
    protected void writeInternal(Object object, final HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        if (object != null) {
            if (object instanceof AppErrorCode) {
                AppErrorCode code = (AppErrorCode) object;

                object = Result.error(code);//
            } else {
                object = Result.success(object);
            }
        }

        final PrintableOutputStream printableOutputStream = new PrintableOutputStream(outputMessage.getBody());

        HttpOutputMessage httpOutputMessageWrapper = new HttpOutputMessageWrapper(outputMessage,
                printableOutputStream);

        super.writeInternal(object, httpOutputMessageWrapper);

        Client client = ClientContextHolder.getClient();
        client.getRequestContext().setResponseBody(printableOutputStream.toString());
        client.getRequestContext().setResponseContentLength(printableOutputStream.getLength());
    }

    /**
     * 一个Class是否应该被隐藏信息
     *
     * @param inputClass
     * @return
     */
    protected boolean isIgnoreInvisiable(Class<?> inputClass) {

        if (!this.ignoreInvisiable) {//如果没有开启 隐身模式，则每个类都打开
            return false;
        }

        if (Invisiable.class.isAssignableFrom(inputClass)) {
            return true;
        }

        return false;
    }

    private final class HttpOutputMessageWrapper implements
            HttpOutputMessage {
        private final HttpOutputMessage outputMessage;
        private final PrintableOutputStream printableOutputStream;

        private HttpOutputMessageWrapper(
                HttpOutputMessage outputMessage,
                PrintableOutputStream printableOutputStream) {
            this.outputMessage = outputMessage;
            this.printableOutputStream = printableOutputStream;
        }

        @Override
        public HttpHeaders getHeaders() {
            return outputMessage.getHeaders();
        }

        @Override
        public OutputStream getBody() throws IOException {

            return printableOutputStream;
        }
    }

    private final class HttpInputMessageWrapper implements
            HttpInputMessage {
        private final HttpInputMessage inputMessage;
        private final PrintableInputStream printableInputStream;

        private HttpInputMessageWrapper(HttpInputMessage inputMessage,
                                        PrintableInputStream printableInputStream) {
            this.inputMessage = inputMessage;
            this.printableInputStream = printableInputStream;
        }

        @Override
        public HttpHeaders getHeaders() {
            return inputMessage.getHeaders();
        }

        @Override
        public InputStream getBody() throws IOException {
            return printableInputStream;
        }
    }

}
