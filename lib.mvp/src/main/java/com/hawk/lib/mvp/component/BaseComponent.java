package com.hawk.lib.mvp.component;

import android.support.annotation.NonNull;

import com.hawk.lib.mvp.ui.display.BaseDisplay;
import com.hawk.lib.mvp.ui.presenter.BasePresenter;

/**
 * Created by lan on 2016/10/27.
 */

public interface BaseComponent<V extends BaseDisplay, P extends BasePresenter<V>> {

    @NonNull
    P presenter();

}
