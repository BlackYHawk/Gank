package com.hawk.lib.base.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

/**
 * Created by lan on 2016/7/7.
 */
public class UIHelper {
    private static Toast mToast;
    private static Snackbar mSnackbar;
    private static ProgressDialog progressDialog;

    public static void showToast(Context context, String str) {
        showToast(context, str, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, int stringId) {
        showToast(context, context.getString(stringId), Toast.LENGTH_SHORT);
    }

    private static void showToast(Context context, CharSequence str, int duration) {
        cancelToast();

        mToast = Toast.makeText(context, str, duration);
        mToast.show();
    }

    public static void showSnackbar(View view, String str) {
        showSnackbar(view, str, Snackbar.LENGTH_SHORT);
    }

    private static void showSnackbar(View view, CharSequence str, int duration) {
        cancelSnackbar();

        mSnackbar = Snackbar.make(view, str, duration);
        mSnackbar.show();
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

    public static void cancelToast() {
        if(mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    public static void cancelSnackbar() {
        if(mSnackbar != null) {
            mSnackbar.dismiss();
            mSnackbar = null;
        }
    }

    public static void dismiss() {
        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static void showListDialog(Context context, String[] items,
                                      DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setItems(items, onClickListener)
                .setCancelable(true);
        builder.create().show();
    }

}
