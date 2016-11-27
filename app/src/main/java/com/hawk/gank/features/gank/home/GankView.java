package com.hawk.gank.features.gank.home;

import com.hawk.gank.model.error.RxError;
import com.hawk.lib.mvp.ui.view.BaseView;

/**
 * Created by heyong on 2016/11/8.
 */

public interface GankView extends BaseView<GankUiCallbacks> {

    GankPresenter.GankQueryType getGankQueryType();

    void showError(RxError error);

}
