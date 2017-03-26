package com.hawk.lib.mvp.rx;

import com.hawk.lib.mvp.ui.presenter.BasePresenter;
import com.hawk.lib.mvp.ui.view.BaseView;

import io.reactivex.disposables.Disposable;


/**
 * Created by lan on 2016/10/27.
 */

public abstract class BaseRxPresenter<V extends BaseView<VC>, VC> extends BasePresenter<V, VC> {
    private final RxDelegate mRxDelegate;

    protected BaseRxPresenter() {
        mRxDelegate = new RxDelegate();
    }

    protected boolean addUtilStop(Disposable disposable) {
        return mRxDelegate.addUtilStop(disposable);
    }

    public boolean addUtilDestroy(Disposable disposable) {
        return mRxDelegate.addUtilDestroy(disposable);
    }

    public void remove(Disposable disposable) {
        mRxDelegate.remove(disposable);
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
