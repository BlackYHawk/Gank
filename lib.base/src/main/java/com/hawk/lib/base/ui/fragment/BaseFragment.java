package com.hawk.lib.base.ui.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;

import com.hawk.lib.base.ui.activity.StartActivityDelegate;
import com.hawk.lib.mvp.component.BaseComponent;
import com.hawk.lib.mvp.ui.display.BaseDisplay;
import com.hawk.lib.mvp.ui.fragment.MvpDiFragment;
import com.hawk.lib.mvp.ui.presenter.BasePresenter;

/**
 * Created by lan on 2016/10/27.
 */

public abstract class BaseFragment<V extends BaseDisplay, P extends BasePresenter<V>, C extends BaseComponent<V, P>>
        extends MvpDiFragment<V, P, C> implements TransactionCommitter {
    private final SupportFragmentTransactionDelegate mSupportFragmentTransactionDelegate
            = new SupportFragmentTransactionDelegate();

    protected boolean startActivitySafely(final Intent intent) {
        return StartActivityDelegate.startActivitySafely(this, intent);
    }

    protected final boolean startActivityForResultSafely(final Intent intent, final int code) {
        return StartActivityDelegate.startActivityForResultSafely(this, intent, code);
    }

    protected boolean safeCommit(@NonNull FragmentTransaction transaction) {
        return mSupportFragmentTransactionDelegate.safeCommit(this, transaction);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSupportFragmentTransactionDelegate.onResumed();
    }

    @Override
    public boolean isCommitterResumed() {
        return isResumed();
    }
}
