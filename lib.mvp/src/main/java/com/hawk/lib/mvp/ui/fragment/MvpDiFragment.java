package com.hawk.lib.mvp.ui.fragment;

import android.os.Bundle;

import com.hawk.lib.mvp.component.BaseComponent;
import com.hawk.lib.mvp.component.GetComponent;
import com.hawk.lib.mvp.ui.display.BaseDisplay;
import com.hawk.lib.mvp.ui.presenter.BasePresenter;

/**
 * Created by lan on 2016/10/27.
 */

public abstract class MvpDiFragment<V extends BaseDisplay, P extends BasePresenter<V>, C extends BaseComponent<V, P>>
        extends MvpFragment<V, P> {
    protected P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        C component = ((GetComponent<C>) getActivity()).getComponent();
        mPresenter = component.presenter();
        injectDependence(component);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected P createPresenter() {
        return mPresenter;
    }

    protected abstract void injectDependence(C component);
}
