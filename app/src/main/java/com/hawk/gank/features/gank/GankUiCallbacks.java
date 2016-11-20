package com.hawk.gank.features.gank;

import android.os.Bundle;

import com.hawk.gank.model.gank.Gank;

/**
 * Created by heyong on 2016/11/8.
 */

public interface GankUiCallbacks {

    void showGankDetail(Gank gank, Bundle bundle);

    void onPulledToTop();

    void onScrolledToBottom();

}
