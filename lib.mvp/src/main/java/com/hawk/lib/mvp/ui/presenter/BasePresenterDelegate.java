package com.hawk.lib.mvp.ui.presenter;

import com.hawk.lib.mvp.ui.display.BaseDisplay;
import com.hawk.lib.mvp.ui.view.BaseView;
import com.hawk.lib.mvp.util.Preconditions;

/**
 * Created by lan on 2016/10/27.
 */

public abstract class BasePresenterDelegate<V extends BaseView<VC>, VC, P extends BasePresenter<V, VC>> {
    private P mPresenter;

    public void onCreate(BaseDisplay display) {
        mPresenter = createPresenter();
        checkPresenter();
        mPresenter.setDisplay(display);
        mPresenter.init();
    }

    public void onStart(V view) {
        checkPresenter();
        mPresenter.attachView(view);
    }

    public void onStop(V view) {
        checkPresenter();
        mPresenter.detachView(view);
    }

    public void onDestroy() {
        checkPresenter();
        mPresenter.suspend();
        mPresenter.setDisplay(null);
    }

    protected abstract P createPresenter();

    private void checkPresenter() {
        Preconditions.checkState(mPresenter != null, "You must call YaViewDelegate#onCreate! And createPresenter must return "
                            + "non-null");
    }
}
