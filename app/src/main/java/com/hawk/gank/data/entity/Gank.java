package com.hawk.gank.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by heyong on 16/7/10.
 */
public class Gank {

    public String _id;

    @SerializedName("createdAt")
    public String createdDate;

    public String desc;

    @SerializedName("publishedAt")
    public String publishedDate;

    public String type;

    public String url;

    public boolean used;

    public String who;

}
