package com.hawk.gank.lib.module;


import com.hawk.gank.lib.qualifiers.ActivityScope;
import com.hawk.gank.lib.ui.presenter.BasePresenter;

import dagger.Component;

/**
 * Created by lan on 2016/6/29.
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActModule.class)
public interface ActComponent {

    void inject(BasePresenter basePresenter);

}
