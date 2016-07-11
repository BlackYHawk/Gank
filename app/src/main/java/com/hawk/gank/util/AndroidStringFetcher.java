package com.hawk.gank.util;

import android.content.Context;

import com.hawk.gank.interfaces.StringFetcher;


/**
 * Created by lan on 2016/4/25.
 */
public class AndroidStringFetcher implements StringFetcher {

    private final Context mContext;

    public AndroidStringFetcher(Context context) {
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
}