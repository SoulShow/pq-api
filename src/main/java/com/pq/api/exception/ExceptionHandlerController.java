package com.pq.api.exception;


import com.pq.api.type.Errors;
import com.pq.common.aop.NotSingleException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

/**
 * 这个只是为了内部处理异常的
 */
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理 {@link org.springframework.web.bind.annotation.RequestParam} 绑定的错误
     */
    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex,
                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        //处理参数绑定错误，展示绑定错误异常给客户端
        AppErrorCode error = AppErrorCode.error(Errors.ParamsBindFailed);


        List<ObjectError> allErrors = ex.getAllErrors();
        return getErrorResponseEntity(error, allErrors);
    }

    /**
     * 处理 @RequestBody绑定时跑错
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        AppErrorCode error = AppErrorCode.error(Errors.ParamsBindFailed);

        List<ObjectError> allErrors = result.getAllErrors();
        return getErrorResponseEntity(error, allErrors);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        return responseError(AppErrorCode.error(Errors.MediaTypeNotSupport));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        AppErrorCode error = new AppErrorCode(Errors.ParamsBindFailed, String.format("字段名称：[%s] 是必须填写的", ex.getParameterName()));

        return responseError(error);
    }



    /**
     * 从绑定异常中，获取
     * @param error
     * @param allErrors
     * @return
     */
    private ResponseEntity<Object> getErrorResponseEntity(AppErrorCode error,
                                                          List<ObjectError> allErrors) {
        if (allErrors != null && !allErrors.isEmpty()) {
            //尝试找到消息
            ObjectError oe = allErrors.get(0);

            //这个表明咱们还是使用的默认的参数，得用里面的code去找到模板
            //这种情况，咱们尽量使用自己定义的错误
            if (StringUtils.contains(oe.getDefaultMessage(), "{")
                    && StringUtils.contains(oe.getDefaultMessage(), "}")) {
                if (oe instanceof FieldError) {
                    FieldError fe = (FieldError) oe;
                    error.setMessage(String.format("字段名称为: [%s] 的参数无法正常绑定，请查看格式是否正确", fe.getField()));
                } else {
                    error.setMessage(oe.getDefaultMessage());
                }
            } else {
                error.setMessage(oe.getDefaultMessage());
            }


        }

        return responseError(error);
    }

    /**
     * 内部公用方法，给出错误类就抛出去
     * @param error
     * @return
     */
    private ResponseEntity<Object> responseError(AppErrorCode error) {
        return new ResponseEntity<Object>(error, HttpStatus.OK);
    }

    /**
     * 这个的意义在于，所有注册过的异常都会通过 {@link org.springframework.web.bind.annotation.ExceptionHandler} 注册过的方法走
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(value = {AppException.class,  NotSingleException.class})
    public final ResponseEntity<Object> handleBusinessException(Exception ex, WebRequest request) {


        loggerException(ex);
        if (ex instanceof AppException) {
            AppException se = (AppException) ex;
            AppErrorCode sec = se.getAppErrorCode();

            return responseError(sec);
        }  else if (ex instanceof NotSingleException){
            AppErrorCode appErrorCode =new AppErrorCode();
            appErrorCode.setStatus(Errors.BadRequest.toString());
            appErrorCode.setMessage(((NotSingleException) ex).getErrorCode().getErrorMsg());
            return responseError(appErrorCode);
        }

        return responseError(AppErrorCode.error(Errors.UnkownError));

    }

    private boolean ignoreExceptionDetail(Exception ex) {
        if (ex  instanceof AppException){
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body, HttpHeaders headers, HttpStatus status,
                                                             WebRequest request) {
        loggerException(ex);

        //如果已经被定义了，表明是我们支持的描述，否则抛出未知异常
        if (Errors.getInstance().includes(status.value())) {
            return responseError(AppErrorCode.error(status.value()));
        }

        return responseError(AppErrorCode.error(Errors.UnkownError));
    }

    private void loggerException(Exception ex) {
        if (!ignoreExceptionDetail(ex)) {
            logger.error("发生异常: " + ex.getClass().getSimpleName() + " 内容是: " + ex.getMessage(), ex);
        } else {
            logger.warn("发生可控业务异常: " + ex.getClass().getSimpleName() + " 内容是: " + ex.getMessage());
        }
    }
}
