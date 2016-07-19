package com.hawk.gank.data.entity;

import java.io.Serializable;

/**
 * Created by heyong on 16/7/18.
 */
public class ConsumptionBean implements Serializable {
    private int collectionCount;
    private int shareCount;
    private int replyCount;

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }
}
