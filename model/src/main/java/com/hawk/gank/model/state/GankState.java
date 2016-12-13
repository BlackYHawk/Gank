package com.hawk.gank.model.state;

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

    void setTagList(List<Tag> tagList);

    List<Tag> getTagList();

    void updateTag(Tag tag);

    void setGankAndroid(int viewId, int page, List<Gank> gankList);

    MoviePagedResult getGankAndroid();

    void setGankIos(int viewId, int page, List<Gank> gankList);

    MoviePagedResult getGankIos();

    void setGankWelfare(int viewId, int page, List<Gank> gankList);

    MoviePagedResult getGankWelfare();

    void setGankFront(int viewId, int page, List<Gank> gankList);

    MoviePagedResult getGankFront();

    void setGankExpand(int viewId, int page, List<Gank> gankList);

    MoviePagedResult getGankExpand();

    void setGankVideo(int viewId, int page, List<Gank> gankList);

    MoviePagedResult getGankVideo();

    void setGankCollect(int viewId, int page, List<Gank> gankList);

    MoviePagedResult getGankCollect();

    void notifyRxError(int viewId, RxError rxError);

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

    class MoviePagedResult extends PagedResult<Gank> {}

}
