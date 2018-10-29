package com.pq.api.utils;


import com.pq.api.exception.AppErrorCode;

/**
 *
 * @author liken
 * @date 15/3/14
 */
public class Result<T> {

    private T data;
    private AppErrorCode error;

    public Result(){
    }

    public Result(T data, AppErrorCode error){
        this.data = data;
        this.error = error;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }

    public AppErrorCode getError() {
        return error;
    }
    public void setError(AppErrorCode error) {
        this.error = error;
    }

    /**
     * 通用成功结果对象
     * @return
     */
    public static <T> Result<T> success(T value){
        Result<T> res = new Result<T>();
        res.setData(value);
        return res;
    }

    /**
     * 返回通用错误结果
     * @param error
     * @return
     */
    public static Result<Void> error(AppErrorCode error){
        Result<Void> res = new Result<Void>();
        res.setError(error);
        return res;
    }

    public boolean hasError() {
        return getError() != null;
    }

    @Override
    public String toString() {
        return "Result [data=" + data + ", error=" + error + "]";
    }


}
