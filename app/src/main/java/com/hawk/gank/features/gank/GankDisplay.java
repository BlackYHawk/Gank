package com.hawk.gank.features.gank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hawk.lib.mvp.ui.display.BaseDisplay;
import com.hawk.lib.mvp.util.Preconditions;

/**
 * Created by heyong on 2016/11/8.
 */

public class GankDisplay implements BaseDisplay {

    private final AppCompatActivity mActivity;
    private Toolbar mToolbar;

    public GankDisplay(AppCompatActivity activity) {
        Preconditions.checkNotNull(activity, "activity cannot be null");
        this.mActivity = activity;
    }

    public void showGankDetail(String id, Bundle bundle) {

    }

    @Override
    public void setSupportActionBar(Object toolbar) {
        mToolbar = (Toolbar) toolbar;
    }
}
