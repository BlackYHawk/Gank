package com.hawk.lib.base.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.WindowManager;

import com.hawk.lib.base.R;
import com.hawk.lib.base.module.ActModule;
import com.hawk.lib.base.ui.fragment.SupportFragmentTransactionDelegate;
import com.hawk.lib.base.ui.fragment.TransactionCommitter;
import com.hawk.lib.mvp.component.BaseComponent;
import com.hawk.lib.mvp.ui.activity.MvpDiActivity;
import com.hawk.lib.mvp.ui.presenter.BasePresenter;
import com.hawk.lib.mvp.ui.view.BaseView;
import com.readystatesoftware.systembartint.SystemBarTintManager;

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

    private static final TypedValue sTypedValue = new TypedValue();
    private int mColorPrimaryDark;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        // inject argument first
        super.onCreate(savedInstanceState);
        mIsResumed = true;
        setContentView(getLayoutRes());
        bindView();
        initSystemBarTint();
    }

    @TargetApi(19)
    private void initSystemBarTint(){
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT){
            SystemBarTintManager tintManager = null;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);

            getTheme().resolveAttribute(R.attr.colorPrimaryDark, sTypedValue, true);
            mColorPrimaryDark = sTypedValue.data;

            tintManager.setStatusBarTintColor(ContextCompat.getColor(this, mColorPrimaryDark));
            tintManager.setStatusBarTintEnabled(true);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsResumed = false;
    }

    protected boolean safeCommit(@NonNull final FragmentTransaction transaction) {
        return mSupportFragmentTransactionDelegate.safeCommit(this, transaction);
    }

    protected void setDisplayBack() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setTitle(String text) {
        if(mToolbar != null) {
            mToolbar.setTitle(text);
        }
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
    protected void bindView() {
        if (autoBindViews()) {
            mUnBinder = ButterKnife.bind(this);
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        if (display != null) {
            display.setSupportActionBar(mToolbar);
        }
        else {
            setSupportActionBar(mToolbar);
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

    public final boolean startActivitySafely(final Intent intent) {
        return StartActivityDelegate.startActivitySafely(this, intent);
    }

    protected ActModule getActModule() {
        return new ActModule(this);
    }

}
