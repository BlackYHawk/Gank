package com.hawk.gank.features.gank.home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hawk.gank.AppContext;
import com.hawk.gank.R;
import com.hawk.gank.features.gank.home.gank.GankTabFragment;
import com.hawk.gank.features.gank.home.openeye.EyeListFragment;
import com.hawk.gank.features.gank.home.setting.SettingFragment;
import com.hawk.gank.model.bean.entity.MenuBean;
import com.hawk.gank.ui.widget.CircleImageView;
import com.hawk.gank.util.MenuGenerator;
import com.hawk.lib.base.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.hawk.lib.base.ui.fragment.SupportFragmentTransactionBuilder.transaction;

/**
 * Created by heyong on 2016/11/4.
 */

public class GankActivity extends BaseActivity<GankView, GankUiCallbacks, GankPresenter<GankView>, GankComponent> {
    public static final String CURRENT_FRAGMENT = "CurrentFragment";
    @BindView(R.id.drawLayout) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigationView) NavigationView mNavigationView;
    private CircleImageView ivHead;
    private TextView tvName;
    private int mCurrentMenuId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.gank_title));
        mCurrentMenuId = (savedInstanceState != null ? savedInstanceState.getInt("menu") : -1);

        if(mCurrentMenuId != -1) {
            onMenuClick(MenuGenerator.generateMenu(mCurrentMenuId), true);
        }
        else {
            onMenuClick(MenuGenerator.generateMenu(R.id.menu_pic), true);
        }
    }

    @Override
    protected void bindView() {
        super.bindView();
        if (display instanceof GankDisplay) {
            ((GankDisplay)display).setDrawerLayout(mDrawerLayout);
        }
        View headerView = mNavigationView.getHeaderView(0);
        ivHead = (CircleImageView) headerView.findViewById(R.id.ivHead);
        tvName = (TextView) headerView.findViewById(R.id.tvName);
        mNavigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            ((GankDisplay)display).closeDrawer();

            item.setChecked(true);
            onMenuClick(MenuGenerator.generateMenu(item.getItemId()), false);
            return true;
        }
    };

    private void onMenuClick(MenuBean item, boolean init) {
        mCurrentMenuId = item != null ? item.menuId : R.id.menu_pic;
        Fragment fragment = null;
        switch (mCurrentMenuId) {
            case R.id.menu_pic :
                fragment = GankTabFragment.newInstance();
                break;
            case R.id.menu_video :
                fragment = EyeListFragment.newInstance();
                break;
            case R.id.menu_setting :
                fragment = SettingFragment.newInstance();
                break;
        }
        if(fragment == null) {
            return;
        }


        if (init) {
            safeCommit(transaction(getSupportFragmentManager())
                    .add(R.id.content, fragment, CURRENT_FRAGMENT)
                    .build());
        }
        else {
            safeCommit(transaction(getSupportFragmentManager())
                    .replace(R.id.content, fragment, CURRENT_FRAGMENT)
                    .build());
        }
    }

    @OnClick(R.id.fabBtn)
    void onFabOnClick(View view) {
        mPresenter.onGankFabClick();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mCurrentMenuId != -1)
            outState.putInt("mCurrentMenuId", mCurrentMenuId);
    }

    @Override
    public void onBackPressed() {
        if (display instanceof GankDisplay) {
            ((GankDisplay)display).onBackPressed();
        }
    }

    @Override
    protected void initializeDependence() {
        component = AppContext.getInstance().appComponent().gankComponent(getActModule());
    }

    @Override
    protected void initializDisplay() {
        display = new GankDisplay(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_ui_main;
    }

}
