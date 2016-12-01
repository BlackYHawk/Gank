package com.hawk.lib.base.ui.presenter;

import com.hawk.lib.mvp.rx.BaseRxPresenter;
import com.hawk.lib.mvp.ui.view.BaseView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by heyong on 2016/11/25.
 */

public abstract class ExtendPresenter extends BaseRxPresenter<ExtendPresenter.ExtendView,
        ExtendPresenter.ExtendCallbacks> {

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

    public interface ExtendCallbacks {
        void finish();
    }

    public interface ExtendView extends BaseView<ExtendCallbacks> {}

    public interface ExtendListView<T extends Serializable> extends ExtendView {
        void setItems(List<T> items);
    }

    public interface StringListView extends ExtendListView<String> {

    }

}
