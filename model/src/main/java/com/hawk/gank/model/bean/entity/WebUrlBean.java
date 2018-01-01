package com.hawk.gank.model.bean.entity;

import java.io.Serializable;

/**
 * Created by heyong on 16/7/18.
 */
public class WebUrlBean implements Serializable {
    private String raw;
    private String forWeibo;

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getForWeibo() {
        return forWeibo;
    }

    public void setForWeibo(String forWeibo) {
        this.forWeibo = forWeibo;
    }
}
