package com.hawk.gank.util;

import com.hawk.gank.R;
import com.hawk.gank.features.gank.GankPresenter;

/**
 * Created by heyong on 16/7/19.
 */
public class StringUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }


    public static int getStringResId(GankPresenter.GankTab tab) {
        switch (tab) {
            case ANDROID:
                return R.string.android_title;
            case IOS:
                return R.string.ios_title;
            case WELFARE:
                return R.string.welfare_title;
            case VIDEO:
                return R.string.video_title;
            case FROANT:
                return R.string.front_title;
            case EXPAND:
                return R.string.expand_title;
        }
        return 0;
    }

}
