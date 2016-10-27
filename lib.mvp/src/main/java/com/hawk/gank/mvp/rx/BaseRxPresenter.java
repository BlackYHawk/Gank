package com.hawk.gank.mvp.rx;

import com.hawk.gank.mvp.ui.display.BaseDisplay;
import com.hawk.gank.mvp.ui.presenter.BasePresenter;

import rx.Subscription;

/**
 * Created by lan on 2016/10/27.
 */

public abstract class BaseRxPresenter<V extends BaseDisplay> extends BasePresenter<V> {
    private final RxDelegate mRxDelegate;

    protected BaseRxPresenter() {
        mRxDelegate = new RxDelegate();
        mRxDelegate.onCreate();
    }

    protected boolean addUtilStop(Subscription subscription) {
        return mRxDelegate.addUtilStop(subscription);
    }

    public boolean addUtilDestroy(Subscription subscription) {
        return mRxDelegate.addUtilDestroy(subscription);
    }

    public void remove(Subscription subscription) {
        mRxDelegate.remove(subscription);
    }

    @Override
    public void attachUi(V view) {
        super.attachUi(view);
        mRxDelegate.onStart();
    }

    @Override
    public void detachUi() {
        super.detachUi();
        mRxDelegate.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRxDelegate.onDestroy();
    }

}
