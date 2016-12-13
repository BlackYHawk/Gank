package com.hawk.gank.features.gank.home;

import com.hawk.gank.model.bean.Gank;
import com.hawk.gank.model.bean.Tag;

/**
 * Created by heyong on 2016/11/8.
 */

public interface GankUiCallbacks {

    void finish();

    void showGankTag();

    void showGankWeb(Gank gank);

    void showGankWealfare(String url);

    void showGankCollect();

    void updateTag(Tag tag);

    void onPulledToTop();

    void onScrolledToBottom();

}
