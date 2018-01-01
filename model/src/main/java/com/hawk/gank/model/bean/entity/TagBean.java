package com.hawk.gank.model.bean.entity;

import java.io.Serializable;

/**
 * Created by heyong on 16/7/18.
 */
public class TagBean implements Serializable {
    private int id;
    private String name;
    private String actionUrl;
    private Object adTrack;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public Object getAdTrack() {
        return adTrack;
    }

    public void setAdTrack(Object adTrack) {
        this.adTrack = adTrack;
    }
}
