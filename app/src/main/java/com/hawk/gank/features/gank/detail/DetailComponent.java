package com.hawk.gank.features.gank.detail;

import com.hawk.gank.model.gank.GankModule;
import com.hawk.lib.base.component.ExtendComponent;
import com.hawk.lib.base.module.ActModule;
import com.hawk.lib.mvp.qualifiers.ActivityScope;

import dagger.Subcomponent;

/**
 * Created by heyong on 2016/11/25.
 */
@ActivityScope
@Subcomponent(modules = {
        ActModule.class, GankModule.class
})
public interface DetailComponent extends ExtendComponent<DetailPresenter> {


}
