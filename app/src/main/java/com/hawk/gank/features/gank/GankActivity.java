package com.hawk.gank.features.gank;

import android.os.Bundle;

import com.hawk.gank.AppContext;
import com.hawk.gank.features.gank.fragments.GankTabFragment;
import com.hawk.lib.base.ui.activity.BaseActivity;

import static com.hawk.lib.base.ui.fragment.SupportFragmentTransactionBuilder.transaction;

/**
 * Created by heyong on 2016/11/4.
 */

public class GankActivity extends BaseActivity<GankView, GankUiCallbacks, GankPresenter<GankView>, GankComponent> {

    public static final String CURRENT_FRAGMENT = "CurrentFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null
                || getSupportFragmentManager().findFragmentByTag(CURRENT_FRAGMENT) == null) {
            showContent();
        }
    }

    private void showContent() {
        safeCommit(transaction(getSupportFragmentManager())
                .add(android.R.id.content, new GankTabFragment(), CURRENT_FRAGMENT)
                .build());
    }

    @Override
    protected void initializeDependence() {
        component = AppContext.getInstance().appComponent().gankComponent(getActModule());
    }

    @Override
    protected void initializDisplay() {
        display = new GankDisplay(this);
    }
}
