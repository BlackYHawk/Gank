package com.hawk.lib.base.component;

import com.hawk.lib.base.ui.presenter.ExtendPresenter;
import com.hawk.lib.mvp.component.BaseComponent;
import com.hawk.lib.mvp.rx.BaseRxPresenter;

/**
 * Created by heyong on 2016/11/25.
 */

public interface ExtendComponent<P extends BaseRxPresenter<ExtendPresenter.ExtendView, ExtendPresenter.ExtendCallbacks>>
        extends BaseComponent<ExtendPresenter.ExtendView, ExtendPresenter.ExtendCallbacks, P> {

}
