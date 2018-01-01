package com.hawk.gank.features.gank.detail;

import com.hawk.lib.base.ui.presenter.ExtendPresenter;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by heyong on 2016/11/25.
 */

public class DetailPresenter extends ExtendPresenter {
    private static final String TAG = DetailPresenter.class.getSimpleName();

    @Inject
    DetailPresenter() {
        super();
    }

    @Override
    protected void onInited() {
        Timber.tag(TAG).e("onInited");
        super.onInited();
    }

    @Override
    protected void onSuspended() {
        Timber.tag(TAG).e("onSuspended");
        super.onSuspended();
    }

}
