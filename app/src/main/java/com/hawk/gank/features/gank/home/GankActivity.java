package com.hawk.gank.features.gank.home;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.hawk.gank.AppContext;
import com.hawk.gank.R;
import com.hawk.gank.features.gank.home.fragments.GankTabFragment;
import com.hawk.gank.features.gank.home.fragments.SideMenuFragment;
import com.hawk.lib.base.ui.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.hawk.lib.base.ui.fragment.SupportFragmentTransactionBuilder.transaction;

/**
 * Created by heyong on 2016/11/4.
 */

public class GankActivity extends BaseActivity<GankView, GankUiCallbacks, GankPresenter<GankView>, GankComponent> {

    public static final String SIDEMENU_FRAGMENT = "SideMenuFragment";
    public static final String CURRENT_FRAGMENT = "CurrentFragment";
    @BindView(R.id.drawLayout) DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.gank_title));

        if (savedInstanceState == null) {
            if(getSupportFragmentManager().findFragmentByTag(CURRENT_FRAGMENT) == null) {
                showContent();
            }
            if(getSupportFragmentManager().findFragmentByTag(SIDEMENU_FRAGMENT) == null) {
                setupSideMenu();
            }
        }
    }

    @Override
    protected void bindView() {
        super.bindView();
        if (display instanceof GankDisplay) {
            ((GankDisplay)display).setDrawerLayout(mDrawerLayout);
        }
    }

    private void setupSideMenu() {
        safeCommit(transaction(getSupportFragmentManager())
                .add(R.id.slidemenu, new SideMenuFragment(), SIDEMENU_FRAGMENT)
                .build());
    }

    private void showContent() {
        safeCommit(transaction(getSupportFragmentManager())
                .add(R.id.content, new GankTabFragment(), CURRENT_FRAGMENT)
                .build());
    }

    @OnClick(R.id.fabBtn)
    void onFabOnClick(View view) {
        mPresenter.onGankFabClick();
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
