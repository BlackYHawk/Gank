package com.hawk.gank.http.convert;

/**
 * Created by heyong on 16/7/24.
 */
public class Result<T> {

    private int code;  // 返回状态：0 失败   1 成功
    private String error;  // 返回信息
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
