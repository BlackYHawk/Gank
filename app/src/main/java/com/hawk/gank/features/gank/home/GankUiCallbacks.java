package com.hawk.gank.features.gank.home;

import com.hawk.gank.model.bean.Gank;
import com.hawk.gank.model.bean.Tag;
import com.hawk.gank.model.bean.entity.ItemBean;

/**
 * Created by heyong on 2016/11/8.
 */

public interface GankUiCallbacks {

    void finish();

    void showGankTag();

    void showGankWeb(Gank gank);

    void showGankWealfare(String url);

    void showGankCollect();

    void showBug();

    void updateTag(Tag tag);

    void showOpenEye(ItemBean itemBean);

    void onPulledToTop();

    void onScrolledToBottom();

}
