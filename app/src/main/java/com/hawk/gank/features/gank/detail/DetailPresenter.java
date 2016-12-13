package com.hawk.gank.features.gank.detail;

import com.hawk.gank.model.repository.GankRepo;
import com.hawk.gank.model.state.GankState;
import com.hawk.lib.base.ui.presenter.ExtendPresenter;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by heyong on 2016/11/25.
 */

public class DetailPresenter extends ExtendPresenter {
    private static final String TAG = DetailPresenter.class.getSimpleName();
    private final GankState mGankState;
    private final GankRepo mGankRepo;

    @Inject
    DetailPresenter(final GankRepo gankRepo, final GankState gankState) {
        super();
        mGankRepo = gankRepo;
        mGankState = gankState;
    }

    @Override
    protected void onInited() {
        Timber.tag(TAG).e("onInited");
        super.onInited();
        mGankState.registerForEvent(this);
    }

    @Override
    protected void onSuspended() {
        Timber.tag(TAG).e("onSuspended");
        super.onSuspended();
        mGankState.unregisterForEvent(this);
    }

}
