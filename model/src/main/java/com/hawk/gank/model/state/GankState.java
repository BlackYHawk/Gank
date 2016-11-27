package com.hawk.gank.model.state;

import com.hawk.gank.model.error.RxError;
import com.hawk.gank.model.gank.Gank;
import com.hawk.lib.base.state.BaseState;

import java.util.List;

/**
 * Created by heyong on 2016/11/15.
 */

public interface GankState extends BaseState {

    void setTypeList(List<String> typeList);

    List<String> getTypeList();

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

    void notifyRxError(int viewId, RxError rxError);

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

    class GankFabClickEvent {}

    class MoviePagedResult extends PagedResult<Gank> {}

}
