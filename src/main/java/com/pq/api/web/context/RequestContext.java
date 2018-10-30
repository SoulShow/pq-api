package com.pq.api.web.context;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 请求上下文，记录在Client中，不依赖 HttpRequestServlet，挂在Client对象中
 * 这里记录跟 http request servlet 相关的，请求头、响应头，响应内容
 *
 * @author liken
 * @date 15/3/13
 */
@Component
public class RequestContext {

    /**
     * 请求的参数，传统的键值对
     */
    private String requestParams;

    /**
     * 请求的参数内容
     */
    private String requestBody;

    private int requestBodyLength;

    /**
     * 返回的请求体
     */
    private String responseBody;

    /**
     * 响应的字节大小
     */
    private int responseContentLength;

    /**
     * 标记当前请求的ID
     */
    private String requestUUID;

    /**
     * 记住请求头
     */
    private String requestHeaders;

    /**
     * 请求的路径，之所以是List，因为内部可能多次转发，而这个不适合在 {@link SimpleClientResolver}中去做
     * 因为这个应该只会走一次
     */
    private List<String> requestPaths;

    private long requestStartAt;

    /**
     * 在读取数据失败时，可能无法打印数据
     */
    private boolean requestPrinted;

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        if (!hasRequestParams()) {
            this.requestParams = requestParams;
        }
    }

    public boolean hasRequestParams() {
        return this.requestParams != null;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        if (!hasRequestBody()) {
            this.requestBody = requestBody;
        }
    }

    public boolean hasRequestBody() {
        return this.requestBody != null;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        if (!hasResponseBody()) {
            this.responseBody = responseBody;
        }
    }

    public boolean hasResponseBody() {
        return this.responseBody != null;
    }

    public String getRequestUUID() {
        return requestUUID;
    }

    public void setRequestUUID(String requestUUID) {
        if (!hasRequestUUID()) {
            this.requestUUID = requestUUID;
        }
    }

    public int getRequestBodyLength() {
        return requestBodyLength;
    }

    public void setRequestBodyLength(int requestBodyLength) {
        if (this.requestBodyLength <= 0) {
            this.requestBodyLength = requestBodyLength;
        }
    }

    public boolean hasRequestUUID() {
        return this.requestUUID != null;
    }

    public String getRequestPaths() {
        if (this.requestPaths == null || this.requestPaths.isEmpty()) {
            return "";
        }
        return Joiner.on("=>").join(this.requestPaths).toString();
    }

    public void addRequestPath(String requestPath) {
        if (this.requestPaths == null) {
            this.requestPaths = Lists.newArrayListWithCapacity(5);
        }

        this.requestPaths.add(requestPath);
    }


    /**
     * 标记请求开始，记录时间戳
     */
    public void markRequestStarted() {
        if (this.requestStartAt <= 0l) {
//            this.requestStartAt = SystemTime.asMillis();
            this.requestStartAt = System.currentTimeMillis();

        }
    }

    public long getRequestStartAt() {
        return requestStartAt;
    }

    public boolean hasRequestHeaders() {
        return this.requestHeaders != null;
    }

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(String requestHeaders) {
        if (!hasRequestHeaders()) {
            this.requestHeaders = requestHeaders;
        }
    }

    public int getResponseContentLength() {
        return responseContentLength;
    }

    public void setResponseContentLength(int responseContentLength) {
        this.responseContentLength = responseContentLength;
    }

    public boolean isForwardRequest() {
        if (this.requestPaths == null || this.requestPaths.isEmpty()) {
            return false;
        }

        return this.requestPaths.size() > 1;//路径有多个，基本上就是forward的请求
    }

    @Override
    public String toString() {
        return "RequestContext [requestParams=" + requestParams
                + ", requestBody=" + requestBody + ", requestBodyLength="
                + requestBodyLength + ", responseBody=" + responseBody
                + ", responseContentLength=" + responseContentLength
                + ", requestUUID=" + requestUUID + ", requestHeaders="
                + requestHeaders + ", requestPaths=" + requestPaths
                + ", requestStartAt=" + requestStartAt + ", requestPrinted="
                + requestPrinted + "]";
    }

    public boolean isRequestPrinted() {
        return requestPrinted;
    }

    public void setRequestPrinted(boolean requestPrinted) {
        this.requestPrinted = requestPrinted;
    }

}
