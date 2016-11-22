package com.hawk.lib.base.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hawk.lib.base.R;
import com.hawk.lib.base.module.ActModule;
import com.hawk.lib.base.ui.fragment.SupportFragmentTransactionDelegate;
import com.hawk.lib.base.ui.fragment.TransactionCommitter;
import com.hawk.lib.mvp.component.BaseComponent;
import com.hawk.lib.mvp.ui.activity.MvpDiActivity;
import com.hawk.lib.mvp.ui.presenter.BasePresenter;
import com.hawk.lib.mvp.ui.view.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lan on 2016/10/27.
 */

public abstract class BaseActivity<V extends BaseView<VC>, VC, P extends BasePresenter<V, VC>,
        C extends BaseComponent<V, VC, P>> extends MvpDiActivity<V, VC, P, C> implements TransactionCommitter {
    private final SupportFragmentTransactionDelegate mSupportFragmentTransactionDelegate
            = new SupportFragmentTransactionDelegate();
    private volatile boolean mIsResumed;
    private Unbinder mUnBinder;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        // inject argument first
        super.onCreate(savedInstanceState);
        mIsResumed = true;
        setContentView(getLayoutRes());
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        display.setSupportActionBar(mToolbar);
        bindView(view);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindView();
    }

    protected boolean autoBindViews() {
        return true;
    }

    @CallSuper
    protected void bindView(final View rootView) {
        if (autoBindViews()) {
            mUnBinder = ButterKnife.bind(this, rootView);
        }
    }

    protected void unbindView() {
        if (autoBindViews() && mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    protected int getLayoutRes() {
        return 0;
    }

    protected final boolean startActivitySafely(final Intent intent) {
        return StartActivityDelegate.startActivitySafely(this, intent);
    }

    protected ActModule getActModule() {
        return new ActModule(this);
    }

}
