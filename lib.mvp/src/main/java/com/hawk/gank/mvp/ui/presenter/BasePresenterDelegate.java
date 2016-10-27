package com.hawk.gank.mvp.ui.presenter;

import com.hawk.gank.mvp.ui.display.BaseDisplay;
import com.hawk.gank.mvp.util.Preconditions;

/**
 * Created by lan on 2016/10/27.
 */

public abstract class BasePresenterDelegate<V extends BaseDisplay, P extends BasePresenter<V>> {
    private P mPresenter;

    public void onCreate() {
        mPresenter = createPresenter();
        checkPresenter();
    }

    public void onStart(V view) {
        checkPresenter();
        mPresenter.attachUi(view);
    }

    public void onStop() {
        checkPresenter();
        mPresenter.detachUi();
    }

    public void onDestroy() {
        checkPresenter();
        mPresenter.onDestroy();
    }

    protected abstract P createPresenter();

    private void checkPresenter() {
        Preconditions.checkState(mPresenter != null, "You must call YaViewDelegate#onCreate! And createPresenter must return "
                            + "non-null");
    }
}
