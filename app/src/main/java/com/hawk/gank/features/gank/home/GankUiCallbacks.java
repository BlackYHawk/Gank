package com.hawk.gank.features.gank.home;

import com.hawk.gank.model.bean.Tag;

/**
 * Created by heyong on 2016/11/8.
 */

public interface GankUiCallbacks {

    void finish();

    void showGankTag();

    void showGankWeb(String url);

    void showGankWealfare(String url);

    void updateTag(Tag tag);

    void onPulledToTop();

    void onScrolledToBottom();

}
