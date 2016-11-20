package com.hawk.gank.model.state;

import com.hawk.gank.model.gank.Gank;
import com.hawk.lib.base.state.BaseState;

import java.util.List;

/**
 * Created by heyong on 2016/11/15.
 */

public interface GankState extends BaseState {

    void setGankAndroid(int page, List<Gank> gankList);

    MoviePagedResult getGankAndroid();

    void setGankIos(int page, List<Gank> gankList);

    MoviePagedResult getGankIos();

    void setGankWelfare(int page, List<Gank> gankList);

    MoviePagedResult getGankWelfare();

    class WelfareListChangedEvent {}

    class AndroidListChangedEvent {}

    class IosListChangedEvent {}

    class MoviePagedResult extends PagedResult<Gank> {}

}
