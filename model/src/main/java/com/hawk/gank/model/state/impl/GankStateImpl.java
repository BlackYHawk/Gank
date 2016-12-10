package com.hawk.gank.model.state.impl;

import com.hawk.gank.model.error.RxError;
import com.hawk.gank.model.bean.Gank;
import com.hawk.gank.model.bean.Tag;
import com.hawk.gank.model.state.GankState;
import com.hawk.lib.base.util.ObjectUtil;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by heyong on 2016/11/15.
 */
@Singleton
public class GankStateImpl implements GankState {
    private final Bus mEventBus;
    private List<Tag> mTagList;
    private MoviePagedResult mAndroid;
    private MoviePagedResult mIos;
    private MoviePagedResult mWelfare;
    private MoviePagedResult mFront;
    private MoviePagedResult mExpand;
    private MoviePagedResult mVideo;

    @Inject
    public GankStateImpl(final Bus bus) {
        this.mEventBus = bus;
    }

    @Override
    public void setTagList(List<Tag> tagList) {
        if(!ObjectUtil.equal(mTagList, tagList)) {
            mTagList = tagList;
            mEventBus.post(new GankTabEvent());
        }
    }

    @Override
    public List<Tag> getTagList() {
        return mTagList;
    }

    @Override
    public void updateTag(Tag tag) {
        for (int i=0; mTagList != null && i<mTagList.size(); i++) {
            if(mTagList.get(i).type().equals(tag.type())) {
                mTagList.set(i, tag);
                break;
            }
        }
        mEventBus.post(new GankTabEvent());
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
    public void setGankFront(int viewId, int page, List<Gank> gankList) {
        if(mFront == null) {
            mFront = createPagedResult();
        }
        updatePagedResult(mFront, page, gankList);
        mEventBus.post(new GankListChangedEvent(viewId));
    }

    @Override
    public MoviePagedResult getGankFront() {
        return mFront;
    }

    @Override
    public void setGankExpand(int viewId, int page, List<Gank> gankList) {
        if(mExpand == null) {
            mExpand = createPagedResult();
        }
        updatePagedResult(mExpand, page, gankList);
        mEventBus.post(new GankListChangedEvent(viewId));
    }

    @Override
    public MoviePagedResult getGankExpand() {
        return mExpand;
    }

    @Override
    public void setGankVideo(int viewId, int page, List<Gank> gankList) {
        if(mVideo == null) {
            mVideo = createPagedResult();
        }
        updatePagedResult(mVideo, page, gankList);
        mEventBus.post(new GankListChangedEvent(viewId));
    }

    @Override
    public MoviePagedResult getGankVideo() {
        return mVideo;
    }

    @Override
    public void notifyRxError(int viewId, RxError rxError) {
        mEventBus.post(new GankRxErrorEvent(viewId, rxError));
    }

    @Override
    public void notifyCollect() {
        mEventBus.post(new GankCollectEvent());
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
