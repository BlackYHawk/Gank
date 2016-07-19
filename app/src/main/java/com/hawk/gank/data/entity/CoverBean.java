package com.hawk.gank.data.entity;

import java.io.Serializable;

/**
 * Created by heyong on 16/7/18.
 */
public class CoverBean implements Serializable {
    private String feed;
    private String detail;
    private String blurred;
    private Object sharing;

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getBlurred() {
        return blurred;
    }

    public void setBlurred(String blurred) {
        this.blurred = blurred;
    }

    public Object getSharing() {
        return sharing;
    }

    public void setSharing(Object sharing) {
        this.sharing = sharing;
    }
}
