package com.hawk.gank.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by lan on 2016/7/7.
 */
public class UIHelper {
    private static Toast mToast;
    private static ProgressDialog progressDialog;

    public static void showToast(Context context, String str) {
        showToast(context, str, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, int stringId) {
        showToast(context, context.getString(stringId), Toast.LENGTH_SHORT);
    }

    private static void showToast(Context context, CharSequence str, int duration) {
        if(mToast != null) {
            mToast.cancel();
            mToast = null;
        }

        mToast = Toast.makeText(context, str, duration);
        mToast.show();
    }

    public static void showProgress(Context context, int strId) {
        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

        String msg = context.getString(strId);
        progressDialog = ProgressDialog.show(context, "", msg, true, true);
    }

    public static void showProgressValue(Context context, int strId) {
        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }

        String msg = context.getString(strId);
        progressDialog = ProgressDialog.show(context, "", msg, true, true);
    }

    public static void setProgress(int progress) {
        if(progressDialog != null) {
            progressDialog.setMessage(progress + "%");
        }
    }

    public static void dismiss() {
        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

}
