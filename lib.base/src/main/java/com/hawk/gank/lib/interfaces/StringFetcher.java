package com.hawk.gank.lib.interfaces;

/**
 * Created by lan on 2016/4/25.
 */
public interface StringFetcher {

    int getDrawableId(String imgName);

    String getStringRes(int strId);

    int getColorRes(int colorId);

    float getDimensionRes(int dimenId);

}
