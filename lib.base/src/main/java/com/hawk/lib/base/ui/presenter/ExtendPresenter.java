package com.hawk.lib.base.ui.presenter;

import com.hawk.lib.mvp.rx.BaseRxPresenter;
import com.hawk.lib.mvp.ui.view.BaseView;

/**
 * Created by heyong on 2016/11/25.
 */

public abstract class ExtendPresenter extends BaseRxPresenter<ExtendPresenter.ExtendView,
        ExtendPresenter.ExtendCallbacks> {

    public interface ExtendCallbacks {
        void finish();
    }

    public interface ExtendView extends BaseView<ExtendCallbacks> {}

    private ExtendCallbacks mCallbacks;

    @Override
    protected ExtendCallbacks createUiCallbacks(ExtendView view) {
        return new ExtendCallbacks() {
            @Override
            public void finish() {
                if(mCallbacks != null) {
                    mCallbacks.finish();
                }
            }
        };
    }

    public void setHostCallbacks(ExtendCallbacks extendCallbacks) {
        mCallbacks = extendCallbacks;
    }

}
