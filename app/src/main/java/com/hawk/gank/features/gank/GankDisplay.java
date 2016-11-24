package com.hawk.gank.features.gank;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hawk.gank.R;
import com.hawk.gank.features.extend.WebActivity;
import com.hawk.lib.mvp.ui.display.BaseDisplay;
import com.hawk.lib.mvp.util.Preconditions;

/**
 * Created by heyong on 2016/11/8.
 */

public class GankDisplay implements BaseDisplay {

    private final AppCompatActivity mActivity;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    public GankDisplay(AppCompatActivity activity) {
        Preconditions.checkNotNull(activity, "activity cannot be null");
        this.mActivity = activity;
    }

    public void showGankWeb(String url) {
        Intent intent = new Intent(mActivity, WebActivity.class);
        intent.putExtra("url", url);
        mActivity.startActivity(intent);
    }

    @Override
    public void setDrawerLayout(DrawerLayout drawerLayout) {
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
