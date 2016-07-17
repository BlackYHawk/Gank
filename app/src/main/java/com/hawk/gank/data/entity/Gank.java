package com.hawk.gank.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by heyong on 16/7/10.
 */
public class Gank implements Serializable, Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.createdDate);
        dest.writeString(this.desc);
        dest.writeString(this.publishedDate);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeByte(this.used ? (byte) 1 : (byte) 0);
        dest.writeString(this.who);
    }

    public Gank() {
    }

    protected Gank(Parcel in) {
        this._id = in.readString();
        this.createdDate = in.readString();
        this.desc = in.readString();
        this.publishedDate = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.used = in.readByte() != 0;
        this.who = in.readString();
    }

    public static final Creator<Gank> CREATOR = new Creator<Gank>() {
        @Override
        public Gank createFromParcel(Parcel source) {
            return new Gank(source);
        }

        @Override
        public Gank[] newArray(int size) {
            return new Gank[size];
        }
    };
}
