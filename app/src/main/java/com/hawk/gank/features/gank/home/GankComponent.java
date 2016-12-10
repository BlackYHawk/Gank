package com.hawk.gank.features.gank.home;

import com.hawk.gank.model.bean.GankModule;
import com.hawk.lib.base.module.ActModule;
import com.hawk.lib.mvp.component.BaseComponent;
import com.hawk.lib.mvp.qualifiers.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by heyong on 2016/11/6.
 */
@ActivityScope
@Subcomponent(modules = {
        ActModule.class, GankModule.class
})
public interface GankComponent extends BaseComponent<GankView, GankUiCallbacks, GankPresenter<GankView>> {



}
