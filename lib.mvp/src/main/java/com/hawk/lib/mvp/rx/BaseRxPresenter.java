package com.hawk.lib.mvp.rx;

import com.hawk.lib.mvp.ui.view.BaseView;
import com.hawk.lib.mvp.ui.presenter.BasePresenter;

import rx.Subscription;

/**
 * Created by lan on 2016/10/27.
 */

public abstract class BaseRxPresenter<V extends BaseView<VC>, VC> extends BasePresenter<V, VC> {
    private final RxDelegate mRxDelegate;

    protected BaseRxPresenter() {
        mRxDelegate = new RxDelegate();
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
    public void init() {
        mRxDelegate.onCreate();
        super.init();
    }

    @Override
    public void resume() {
        super.resume();
        mRxDelegate.onStart();
    }

    @Override
    public void pause() {
        super.pause();
        mRxDelegate.onStop();
    }

    @Override
    public void suspend() {
        mRxDelegate.onDestroy();
        super.suspend();
    }

}
