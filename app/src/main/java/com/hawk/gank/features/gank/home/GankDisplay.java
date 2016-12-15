package com.hawk.gank.features.gank.home;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hawk.gank.R;
import com.hawk.gank.features.gank.detail.DetailWelfareActivity;
import com.hawk.gank.features.gank.home.fragments.CollectListFragment;
import com.hawk.gank.features.gank.home.fragments.TagListFragment;
import com.hawk.gank.model.bean.Gank;
import com.hawk.gank.model.qualifier.CollectType;
import com.hawk.lib.base.ui.activity.BaseActivity;
import com.hawk.lib.base.util.UIHelper;
import com.hawk.lib.mvp.ui.display.BaseDisplay;
import com.hawk.lib.mvp.util.Preconditions;

import static com.hawk.gank.model.qualifier.CollectType.DELETE;
import static com.hawk.gank.model.qualifier.CollectType.INSERT;
import static com.hawk.gank.model.qualifier.CollectType.QUERY;

/**
 * Created by heyong on 2016/11/8.
 */

public class GankDisplay implements BaseDisplay {

    private final BaseActivity mActivity;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private long mExitTime = 0;

    public GankDisplay(BaseActivity activity) {
        Preconditions.checkNotNull(activity, "activity cannot be null");
        this.mActivity = activity;
    }

    public void showGankTag() {
        Intent intent = new Intent(mActivity, GankContainerActivity.class);
        intent.putExtra("className", TagListFragment.class.getName());
        mActivity.startActivitySafely(intent);
    }

    public void showGankWeb(Gank gank) {
        Intent intent = new Intent(mActivity, GankWebActivity.class);
        intent.putExtra("gank", gank);
        mActivity.startActivitySafely(intent);
    }

    public void showGankImage(String url) {
        Intent intent = new Intent(mActivity, DetailWelfareActivity.class);
        intent.putExtra("url", url);
        mActivity.startActivitySafely(intent);
    }

    public void showGankCollect() {
        Intent intent = new Intent(mActivity, GankContainerActivity.class);
        intent.putExtra("className", CollectListFragment.class.getName());
        mActivity.startActivitySafely(intent);
    }

    public void collectGank(@CollectType int type) {
        if (mActivity instanceof GankWebActivity) {
            switch (type) {
                case INSERT:
                    UIHelper.showSnackbar(mToolbar, mActivity.getString(R.string.gank_collect));
                    break;
                case DELETE:
                    UIHelper.showSnackbar(mToolbar, mActivity.getString(R.string.gank_collect_cancel));
                    ((GankWebActivity) mActivity).menuValidate(false);
                    break;
                case QUERY:
                    ((GankWebActivity) mActivity).menuValidate(true);
                    break;
            }
        }
    }

    @Override
    public void finish() {
        mActivity.finishTransition();
    }

    @Override
    public void showUpNavigation(boolean show) {
        final ActionBar actionBar = mActivity.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(show);
            actionBar.setHomeButtonEnabled(show);
        }
    }

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

    public void onBackPressed() {
        boolean status = true;

        if(isDrawerOpen()) {
            closeDrawer();
            status = false;
        }

        if(status) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                UIHelper.showToast(mActivity, R.string.exit_hint);
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

}
