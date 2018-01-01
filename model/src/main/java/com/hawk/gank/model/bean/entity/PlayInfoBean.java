package com.hawk.gank.model.bean.entity;

import java.io.Serializable;

/**
 * Created by heyong on 16/7/18.
 */
public class PlayInfoBean implements Serializable {
    private int height;
    private int width;
    private String name;
    private String type;
    private String url;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
