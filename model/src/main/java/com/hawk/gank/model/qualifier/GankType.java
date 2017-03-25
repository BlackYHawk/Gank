package com.hawk.gank.model.qualifier;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.hawk.gank.model.qualifier.GankType.ANDROID;
import static com.hawk.gank.model.qualifier.GankType.EXPAND;
import static com.hawk.gank.model.qualifier.GankType.FROANT;
import static com.hawk.gank.model.qualifier.GankType.IOS;
import static com.hawk.gank.model.qualifier.GankType.VIDEO;
import static com.hawk.gank.model.qualifier.GankType.WELFARE;

/**
 * Created by heyong on 2016/12/13.
 */
@IntDef(flag=true, value = {ANDROID, IOS, WELFARE, VIDEO, FROANT, EXPAND})
@Retention(RetentionPolicy.SOURCE)
public @interface GankType {
    int ANDROID = 0;
    int IOS = 1;
    int WELFARE = 2;
    int VIDEO = 3;
    int FROANT = 4;
    int EXPAND = 5;
}
