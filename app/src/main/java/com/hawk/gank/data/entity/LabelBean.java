package com.hawk.gank.data.entity;

import java.io.Serializable;

/**
 * Created by heyong on 16/7/18.
 */
public class LabelBean implements Serializable {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
