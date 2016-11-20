package com.hawk.lib.base.model.util;

/**
 * Created by lan on 2016/4/25.
 */
public interface ResDelegate {

    int getDrawableId(String imgName);

    String getStringRes(int strId);

    int getColorRes(int colorId);

    float getDimensionRes(int dimenId);

}
