package com.hawk.gank.features.gank.detail;

import com.hawk.gank.AppContext;
import com.hawk.lib.base.ui.activity.ExtendActivity;

/**
 * Created by heyong on 2016/11/27.
 */

public class DetailTagActivity extends ExtendActivity<DetailPresenter, DetailComponent> {


    @Override
    protected void initializeDependence() {
        component = AppContext.getInstance().appComponent().detailComponent(getActModule());
    }
}
