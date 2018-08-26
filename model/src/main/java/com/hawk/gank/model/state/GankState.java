package com.hawk.gank.model.state;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.hawk.gank.model.bean.Gank;
import com.hawk.gank.model.bean.Tag;
import com.hawk.gank.model.error.RxError;
import com.hawk.gank.model.qualifier.CollectType;
import com.hawk.lib.base.state.BaseState;

import java.util.List;

/**
 * Created by heyong on 2016/11/15.
 */

public interface GankState extends BaseState {

    @MainThread
    void setTagList(List<Tag> tagList);

    List<Tag> getTagList();

    @MainThread
    void updateTag(Tag tag);

    @MainThread
    void setGankAndroid(int viewId, int page, List<Gank> gankList);

    GankPagedResult getGankAndroid();

    @MainThread
    void setGankIos(int viewId, int page, List<Gank> gankList);

    GankPagedResult getGankIos();

    @MainThread
    void setGankWelfare(int viewId, int page, List<Gank> gankList);

    GankPagedResult getGankWelfare();

    @MainThread
    void setGankFront(int viewId, int page, List<Gank> gankList);

    GankPagedResult getGankFront();

    @MainThread
    void setGankExpand(int viewId, int page, List<Gank> gankList);

    GankPagedResult getGankExpand();

    @MainThread
    void setGankVideo(int viewId, int page, List<Gank> gankList);

    GankPagedResult getGankVideo();

    @MainThread
    void setGankCollect(int viewId, int page, List<Gank> gankList);

    GankPagedResult getGankCollect();

    @MainThread
    void notifyRxError(int viewId, RxError rxError);

    @MainThread
    void notifyCollect(@NonNull @CollectType int type);

    class GankTabEvent {}

    class GankListChangedEvent extends UiCausedEvent {
        public GankListChangedEvent(int callingId) {
            super(callingId);
        }
    }

    class GankRxErrorEvent extends BaseArgumentEvent<RxError> {
        public GankRxErrorEvent(int callingId, RxError item) {
            super(callingId, item);
        }
    }

    class GankCollectEvent {
        public final @CollectType int type;

        public GankCollectEvent(@CollectType int type) {
            this.type = type;
        }
    }

    class GankPagedResult extends PagedResult<Gank> {
        public GankPagedResult(int pageSize) {
            super(pageSize);
        }
    }

}
