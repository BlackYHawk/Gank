package com.hawk.lib.mvp.ui.presenter;

import com.hawk.lib.mvp.ui.view.BaseView;

/**
 * Created by heyong on 2016/11/8.
 */

public interface GetPresenterDelegate<V extends BaseView<VC>, VC, P extends BasePresenter<V, VC>> {

    BasePresenterDelegate<V, VC, P> getPresenterDelegate();

}
