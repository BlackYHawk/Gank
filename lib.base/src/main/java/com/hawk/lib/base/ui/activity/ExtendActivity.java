package com.hawk.lib.base.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hawk.lib.base.R;
import com.hawk.lib.base.ui.fragment.SupportFragmentTransactionDelegate;
import com.hawk.lib.base.ui.fragment.TransactionCommitter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lan on 2016/10/27.
 */

public abstract class ExtendActivity extends AppCompatActivity implements TransactionCommitter {
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
        bindView();
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

        if(mToolbar != null) {
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

    protected final boolean startActivitySafely(final Intent intent) {
        return StartActivityDelegate.startActivitySafely(this, intent);
    }

}
