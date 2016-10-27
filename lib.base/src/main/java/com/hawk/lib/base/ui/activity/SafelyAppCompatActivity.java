package com.hawk.lib.base.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.hawk.lib.base.ui.fragment.SupportFragmentTransactionDelegate;
import com.hawk.lib.base.ui.fragment.TransactionCommitter;

public class SafelyAppCompatActivity extends AppCompatActivity implements TransactionCommitter {

    private volatile boolean mIsResumed = false;
    private final SupportFragmentTransactionDelegate mSupportFragmentTransactionDelegate =
            new SupportFragmentTransactionDelegate();

    protected boolean startActivitySafely(final Intent intent) {
        return StartActivityDelegate.startActivitySafely(this, intent);
    }

    protected boolean safeCommit(@NonNull FragmentTransaction transaction) {
        return mSupportFragmentTransactionDelegate.safeCommit(this, transaction);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsResumed = true;
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        mIsResumed = true;
        mSupportFragmentTransactionDelegate.onResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsResumed = false;
    }

    @Override
    public boolean isCommitterResumed() {
        return mIsResumed;
    }
}
