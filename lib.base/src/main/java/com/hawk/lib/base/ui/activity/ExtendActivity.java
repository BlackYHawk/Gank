package com.hawk.lib.base.ui.activity;

import android.os.Bundle;

import com.hawk.lib.base.ui.fragment.TransactionCommitter;
import com.hawk.lib.base.ui.presenter.ExtendPresenter;
import com.hawk.lib.mvp.component.BaseComponent;
import com.hawk.lib.mvp.rx.BaseRxPresenter;

/**
 * Created by lan on 2016/10/27.
 */

public abstract class ExtendActivity<P extends BaseRxPresenter<ExtendPresenter.ExtendView, ExtendPresenter.ExtendCallbacks>,
        C extends BaseComponent<ExtendPresenter.ExtendView, ExtendPresenter.ExtendCallbacks, P>>
        extends BaseActivity<ExtendPresenter.ExtendView, ExtendPresenter.ExtendCallbacks, P, C>
        implements ExtendPresenter.ExtendCallbacks, TransactionCommitter {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        // inject argument first
        super.onCreate(savedInstanceState);
        if(mPresenter instanceof ExtendPresenter) {
            ((ExtendPresenter)mPresenter).setHostCallbacks(this);
        }
        onBindData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter instanceof ExtendPresenter) {
            ((ExtendPresenter)mPresenter).setHostCallbacks(null);
        }
    }

    protected void onBindData() {}

}
