package com.pq.api.exception;


import com.pq.api.type.Errors;
import com.pq.common.exception.ErrorCode;

/**
 * 定义应用层异常, 最终会以API的形式给出
 *
 * @author liken
 * @date 15/3/13
 */
public class AppException extends RuntimeException{

    private AppErrorCode appErrorCode;

    /**
     *
     */
    private static final long serialVersionUID = 811554104106763067L;

    public AppException(AppErrorCode appErrorCode) {
        super(String.format("%d: %s", appErrorCode.getStatus(), appErrorCode.getMessage()));

        this.appErrorCode = appErrorCode;
    }

    public AppErrorCode getAppErrorCode() {
        return appErrorCode;
    }

    public void setAppErrorCode(AppErrorCode appErrorCode) {
        this.appErrorCode = appErrorCode;
    }

    /**
     * 生成ApplicationException异常
     * @param code
     */
    public static AppException create(Integer code, Object... values){
        return new AppException(new AppErrorCode(code, (Errors.getInstance().getMessage(code, values)))) ;
    }

    /**
     *  抛ApplicationException异常
     * @param code
     */
    public static void raise(Integer code){
        throw create(code);
    }

    public static void raise(Integer code,String appendMessage){
        throw create(code,appendMessage);
    }

    public static void raise(ErrorCode errorCode){
        throw new AppException(new AppErrorCode(Integer.parseInt(errorCode.getErrorCode()), errorCode.getErrorMsg()));
    }


}
