package com.hawk.gank.model.state.impl;

import com.hawk.gank.model.error.RxError;
import com.hawk.gank.model.gank.Gank;
import com.hawk.gank.model.state.GankState;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by heyong on 2016/11/15.
 */

public class GankStateImpl implements GankState {
    private final Bus mEventBus;
    private MoviePagedResult mAndroid;
    private MoviePagedResult mIos;
    private MoviePagedResult mWelfare;

    @Inject
    public GankStateImpl(final Bus bus) {
        this.mEventBus = bus;
    }

    @Override
    public void setGankAndroid(int viewId, int page, List<Gank> gankList) {
        if(mAndroid == null) {
            mAndroid = createPagedResult();
        }
        updatePagedResult(mAndroid, page, gankList);
        mEventBus.post(new GankListChangedEvent(viewId));
    }

    @Override
    public MoviePagedResult getGankAndroid() {
        return mAndroid;
    }

    @Override
    public void setGankIos(int viewId, int page, List<Gank> gankList) {
        if(mIos == null) {
            mIos = createPagedResult();
        }
        updatePagedResult(mIos, page, gankList);
        mEventBus.post(new GankListChangedEvent(viewId));
    }

    @Override
    public MoviePagedResult getGankIos() {
        return mIos;
    }

    @Override
    public void setGankWelfare(int viewId, int page, List<Gank> welfare) {
        if(mWelfare == null) {
            mWelfare = createPagedResult();
        }
        updatePagedResult(mWelfare, page, welfare);
        mEventBus.post(new GankListChangedEvent(viewId));
    }

    @Override
    public MoviePagedResult getGankWelfare() {
        return mWelfare;
    }

    @Override
    public void notifyRxError(int viewId, RxError rxError) {
        mEventBus.post(new GankRxErrorEvent(viewId, rxError));
    }

    private void updatePagedResult(MoviePagedResult result, int page, List<Gank> gankList) {
        if (page <= 1) {
            result.items.clear();
        }
        result.items.addAll(gankList);
        result.page = page;
    }

    private MoviePagedResult createPagedResult() {
        return new MoviePagedResult();
    }

    @Override
    public void registerForEvent(Object receiver) {
        mEventBus.register(receiver);
    }

    @Override
    public void unregisterForEvent(Object receiver) {
        mEventBus.unregister(receiver);
    }
}
