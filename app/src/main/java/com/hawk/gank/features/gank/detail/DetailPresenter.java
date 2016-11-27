package com.hawk.gank.features.gank.detail;

import com.hawk.gank.model.state.GankState;
import com.hawk.lib.base.ui.presenter.ExtendPresenter;

import javax.inject.Inject;

/**
 * Created by heyong on 2016/11/25.
 */

public class DetailPresenter extends ExtendPresenter {
    private final GankState mGankState;

    @Inject
    DetailPresenter(final GankState gankState) {
        super();
        mGankState = gankState;
    }

    @Override
    protected void onInited() {
        super.onInited();
        mGankState.registerForEvent(this);
    }

    @Override
    protected void onSuspended() {
        super.onSuspended();
        mGankState.unregisterForEvent(this);
    }

}
