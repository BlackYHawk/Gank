package com.hawk.gank.http.convert;

import com.avos.avoscloud.AVException;

import java.io.IOException;

/**
 * Created by heyong on 16/7/24.
 */
public class ResultException extends IOException {

    private int code;  // 返回状态：0 失败   1 成功

    public ResultException(int theCode, String theMessage) {
        super(theMessage);
        this.code = theCode;
    }

    public ResultException(String message, Throwable cause) {
        super(message, cause);
        if(cause instanceof AVException) {
            this.code = ((AVException)cause).getCode();
        }

    }

    public ResultException(Throwable cause) {
        super(cause);

        if(cause instanceof AVException) {
            this.code = ((AVException)cause).getCode();
        }
    }

    public int getCode() {
        return code;
    }

}
