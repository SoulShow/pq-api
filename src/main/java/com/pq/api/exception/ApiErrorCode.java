package com.pq.api.exception;


import com.pq.common.exception.ErrorCode;

public class ApiErrorCode extends ErrorCode {

    private final static String PRE = "0";

    public ApiErrorCode(String errorCode, String errorMsg) {
        super(PRE+errorCode, errorMsg);
    }
}

