package com.pq.api.exception;


import com.pq.api.type.Errors;

/**
 * 错误信息类
 *
 * @author liken
 * @date 15/3/13
 */
public class AppErrorCode {
    private String status;
    private String message;

    public AppErrorCode() {

    }

    public AppErrorCode(Integer status, String message) {
        super();
        this.status = status.toString();
        this.message = message;
    }



    public static AppErrorCode create(String status) {
        return error(Integer.valueOf(status));
    }

    /**
     * 创建错误对象
     * @param code
     * @return
     */
    public static AppErrorCode error(Integer code, Object... values) {
        return new AppErrorCode(code, Errors.getInstance().getMessage(code, values));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

