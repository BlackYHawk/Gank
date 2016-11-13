package com.hawk.lib.base.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;

import com.hawk.lib.base.module.ActModule;
import com.hawk.lib.base.ui.fragment.SupportFragmentTransactionDelegate;
import com.hawk.lib.base.ui.fragment.TransactionCommitter;
import com.hawk.lib.mvp.component.BaseComponent;
import com.hawk.lib.mvp.ui.activity.MvpDiActivity;
import com.hawk.lib.mvp.ui.view.BaseView;
import com.hawk.lib.mvp.ui.presenter.BasePresenter;

/**
 * Created by lan on 2016/10/27.
 */

public abstract class BaseActivity<V extends BaseView<VC>, VC, P extends BasePresenter<V, VC>,
        C extends BaseComponent<V, VC, P>> extends MvpDiActivity<V, VC, P, C> implements TransactionCommitter {
    private final SupportFragmentTransactionDelegate mSupportFragmentTransactionDelegate
            = new SupportFragmentTransactionDelegate();
    private volatile boolean mIsResumed;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        // inject argument first
        super.onCreate(savedInstanceState);
        mIsResumed = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsResumed = false;
    }

    protected boolean safeCommit(@NonNull final FragmentTransaction transaction) {
        return mSupportFragmentTransactionDelegate.safeCommit(this, transaction);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        mIsResumed = true;
        mSupportFragmentTransactionDelegate.onResumed();
    }

    @Override
    public boolean isCommitterResumed() {
        return mIsResumed;
    }

    protected final boolean startActivitySafely(final Intent intent) {
        return StartActivityDelegate.startActivitySafely(this, intent);
    }

    protected ActModule getActModule() {
        return new ActModule(this);
    }

}
