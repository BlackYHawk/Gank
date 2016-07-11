package com.hawk.gank.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by lan on 2016/7/7.
 */
public class UIHelper {

    public static void showToast(Context context, String str) {
        showToast(context, str, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, int stringId) {
        showToast(context, context.getString(stringId), Toast.LENGTH_SHORT);
    }

    private static void showToast(Context context, CharSequence str, int duration) {
        Toast.makeText(context, str, duration).show();
    }
}
