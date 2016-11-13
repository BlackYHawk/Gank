package com.hawk.gank.features.gank;

import com.hawk.lib.mvp.ui.view.BaseView;

/**
 * Created by heyong on 2016/11/8.
 */

public interface GankView extends BaseView<GankUiCallbacks> {

    GankPresenter.GankQueryType getGankQueryType();

}
