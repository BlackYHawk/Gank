package com.hawk.lib.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.hawk.lib.mvp.ui.presenter.GetPresenterDelegate;
import com.hawk.lib.mvp.ui.view.BaseView;
import com.hawk.lib.mvp.ui.presenter.BasePresenter;
import com.hawk.lib.mvp.ui.presenter.BasePresenterDelegate;

/**
 * Created by lan on 2016/6/29.
 */
public abstract class MvpFragment<V extends BaseView<VC>, VC, P extends BasePresenter<V, VC>>
        extends Fragment implements BaseView<VC> {

    private VC mCallbacks;
    BasePresenterDelegate<V, VC, P> mPresenterDelegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenterDelegate = ((GetPresenterDelegate<V, VC, P>) getActivity()).getPresenterDelegate();
    }

    @Override
    public void onStart() {
        mPresenterDelegate.attachView((V)this);
        super.onStart();
    }

    @Override
    public void onStop() {
        mPresenterDelegate.detachView((V)this);
        super.onStop();
    }

    @Override
    public void setCallbacks(VC callbacks) {
        mCallbacks = callbacks;
    }

    protected final boolean hasCallbacks() {
        return mCallbacks != null;
    }

    protected final VC getCallbacks() {
        return mCallbacks;
    }

}
