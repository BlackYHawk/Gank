package com.hawk.gank.ui.activity.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import com.hawk.gank.R;
import com.hawk.gank.data.entity.MenuBean;
import com.hawk.gank.ui.fragment.base.BaseFragment;
import com.hawk.gank.ui.fragment.gank.GankListFragment;
import com.hawk.gank.ui.fragment.openeye.EyeListFragment;
import com.hawk.gank.util.MenuGenerator;

import butterknife.BindView;


/**
 * Created by lan on 2016/6/29.
 */
public class MainActivity extends BaseActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.drawLayout) DrawerLayout mDrawer;
    @BindView(R.id.navigationView) NavigationView mNavigationView;
    private ActionBarDrawerToggle drawerToggle;
    public static final String FRAGMENT_TAG = "MainFragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.e(TAG, "onCreate");
        setContentView(R.layout.ac_ui_main);

        initView();
        onMenuClick(MenuGenerator.generateMenu(R.id.menu_pic));
    }

    private void initView() {
        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

        };
        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            if(mDrawer.isDrawerOpen(GravityCompat.START)) {
                mDrawer.closeDrawer(GravityCompat.START);
            }

            item.setChecked(true);
            onMenuClick(MenuGenerator.generateMenu(item.getItemId()));
            return true;
        }
    };

    private void onMenuClick(MenuBean item) {
        BaseFragment fragment = null;
        switch (item.menuId) {
            case R.id.menu_pic :
                fragment = GankListFragment.newInstance();
                break;
            case R.id.menu_video :
                fragment = EyeListFragment.newInstance();
                break;
        }
        if(fragment == null) {
            return;
        }
        setDisplayTitle(item.titleRes);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        logger.e(TAG, "onDestroy");
        super.onDestroy();
    }

}
