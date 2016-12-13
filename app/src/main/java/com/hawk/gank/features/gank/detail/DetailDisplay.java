package com.hawk.gank.features.gank.detail;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.hawk.lib.base.ui.activity.BaseActivity;
import com.hawk.lib.base.util.UIHelper;
import com.hawk.lib.mvp.ui.display.BaseDisplay;
import com.hawk.lib.mvp.util.Preconditions;

/**
 * Created by heyong on 2016/12/10.
 */

public class DetailDisplay implements BaseDisplay {
    private final BaseActivity mActivity;
    private Toolbar mToolbar;

    public DetailDisplay(BaseActivity activity) {
        Preconditions.checkNotNull(activity, "activity cannot be null");
        this.mActivity = activity;
    }

    @Override
    public void finish() {
        mActivity.finish();
    }

    @Override
    public void showUpNavigation(boolean show) {
        final ActionBar actionBar = mActivity.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(show);
            actionBar.setHomeButtonEnabled(show);
        }
    }

    @Override
    public void setActionBarTitle(String title) {
        mActivity.setTitle(title);
    }

    @Override
    public void setSupportActionBar(Object toolbar) {
        mToolbar = (Toolbar) toolbar;

        if(mToolbar != null) {
            mActivity.setSupportActionBar(mToolbar);

            if(mActivity.getSupportActionBar() != null) {
                mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
    }
}
