package com.hawk.lib.base.model.util;

import android.content.Context;


/**
 * Created by lan on 2016/4/25.
 */
public class ResParser implements ResDelegate {

    private final Context mContext;

    public ResParser(Context context) {
        mContext = context;
    }

    @Override
    public int getDrawableId(String imgName) {
        return mContext.getResources().getIdentifier(imgName, "drawable", mContext.getPackageName());
    }

    @Override
    public String getStringRes(int strId) {
        return mContext.getString(strId);
    }

    @Override
    public int getColorRes(int colorId) {
        return mContext.getResources().getColor(colorId);
    }

    @Override
    public float getDimensionRes(int dimenId) {
        return mContext.getResources().getDimension(dimenId);
    }

}
