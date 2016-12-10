package com.hawk.gank.features.gank.home;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hawk.gank.R;
import com.hawk.gank.features.gank.detail.DetailWebActivity;
import com.hawk.gank.features.gank.detail.DetailWelfareActivity;
import com.hawk.lib.base.ui.activity.BaseActivity;
import com.hawk.lib.mvp.ui.display.BaseDisplay;
import com.hawk.lib.mvp.util.Preconditions;

/**
 * Created by heyong on 2016/11/8.
 */

public class GankDisplay implements BaseDisplay {

    private final BaseActivity mActivity;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    public GankDisplay(BaseActivity activity) {
        Preconditions.checkNotNull(activity, "activity cannot be null");
        this.mActivity = activity;
    }

    public void showGankTag() {
        Intent intent = new Intent(mActivity, GankTagActivity.class);
        mActivity.startActivitySafely(intent);
    }

    public void showGankWeb(String url) {
        Intent intent = new Intent(mActivity, DetailWebActivity.class);
        intent.putExtra("url", url);
        mActivity.startActivitySafely(intent);
    }

    public void showGankImage(String url) {
        Intent intent = new Intent(mActivity, DetailWelfareActivity.class);
        intent.putExtra("url", url);
        mActivity.startActivitySafely(intent);
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
    public void setDrawerLayout(DrawerLayout drawerLayout) {
        Preconditions.checkNotNull(drawerLayout, "drawerLayout can not be null");
        this.mDrawerLayout = drawerLayout;

        drawerToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout, mToolbar,
                R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                mActivity.supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mActivity.supportInvalidateOptionsMenu();
            }

        };
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    @Override
    public void setSupportActionBar(Object toolbar) {
        mToolbar = (Toolbar) toolbar;

        if(mToolbar != null) {
            mActivity.setSupportActionBar(mToolbar);
        }
    }
}
