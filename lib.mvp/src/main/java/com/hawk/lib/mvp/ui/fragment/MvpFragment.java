package com.hawk.lib.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.hawk.lib.mvp.ui.display.BaseDisplay;
import com.hawk.lib.mvp.ui.presenter.BasePresenter;
import com.hawk.lib.mvp.ui.presenter.BasePresenterDelegate;

/**
 * Created by lan on 2016/6/29.
 */
public abstract class MvpFragment<V extends BaseDisplay, P extends BasePresenter<V>>
        extends Fragment implements BaseDisplay {
    private BasePresenterDelegate<V, P> mPresenterDelegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenterDelegate = new BasePresenterDelegate<V, P>() {
            @Override
            protected P createPresenter() {
                return MvpFragment.this.createPresenter();
            }
        };
        mPresenterDelegate.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenterDelegate.onStart((V)this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenterDelegate.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenterDelegate.onDestroy();
    }

    protected abstract P createPresenter();

}
