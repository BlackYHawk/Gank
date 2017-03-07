package com.hawk.gank.features.gank.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hawk.gank.AppContext;
import com.hawk.gank.R;
import com.hawk.gank.features.gank.home.fragments.CollectListFragment;
import com.hawk.gank.features.gank.home.fragments.TagListFragment;
import com.hawk.gank.util.StringUtil;
import com.hawk.lib.base.ui.activity.BaseActivity;
import com.hawk.lib.base.util.ObjectUtil;
import com.hawk.lib.mvp.util.Preconditions;

import static com.hawk.lib.base.ui.fragment.SupportFragmentTransactionBuilder.transaction;

/**
 * Created by heyong on 2016/11/27.
 */

public class GankContainerActivity extends BaseActivity<GankView, GankUiCallbacks, GankPresenter<GankView>, GankComponent> {

    public static final String CURRENT_FRAGMENT = "CurrentFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String className = "";
        Intent intent = getIntent();

        if (!ObjectUtil.isEmpty(intent)) {
            className = getIntent().getStringExtra("className");
        }
        if (ObjectUtil.isEmpty(intent) || StringUtil.isEmpty(className)) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            Fragment fragment = null;
            if (className.equals(TagListFragment.class.getName())) {
                fragment = TagListFragment.newInstance();
            } else if (className.equals(CollectListFragment.class.getName())) {
                fragment = CollectListFragment.newInstance();
            }

            Preconditions.checkNotNull(fragment, "fragment cannot be null");
            safeCommit(transaction(getSupportFragmentManager())
                    .add(R.id.content, fragment, CURRENT_FRAGMENT)
                    .build());
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
        return R.layout.ac_ui_content;
    }
}
