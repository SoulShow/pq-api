package com.pq.api.exception;


import com.pq.common.exception.BaseException;
import com.pq.common.exception.ErrorCode;

/**
 * @author liutao
 */
public class ApiException extends BaseException {

    public ApiException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public ApiException(ErrorCode errorCode) {
        super(errorCode);
    }


    /**
     * 抛UserException异常
     * @param errorCode
     */
    public static void raise(ErrorCode errorCode){
        throw new ApiException(errorCode);
    }


    /**
     * 抛UserException异常
     * @param errorCode
     * @param cause 异常
     */
    public static void raise(ErrorCode errorCode, Throwable cause){
        throw new ApiException(errorCode, cause);
    }
}
