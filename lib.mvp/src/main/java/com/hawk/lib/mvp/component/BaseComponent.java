package com.hawk.lib.mvp.component;

import android.support.annotation.NonNull;

import com.hawk.lib.mvp.ui.view.BaseView;
import com.hawk.lib.mvp.ui.presenter.BasePresenter;

/**
 * Created by lan on 2016/10/27.
 */

public interface BaseComponent<V extends BaseView<VC>, VC, P extends BasePresenter<V, VC>> {

    @NonNull
    P presenter();

}
