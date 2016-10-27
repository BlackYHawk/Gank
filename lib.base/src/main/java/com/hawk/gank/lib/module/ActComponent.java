package com.hawk.gank.lib.module;


import com.hawk.gank.lib.qualifiers.ActivityScope;
import com.hawk.gank.lib.ui.activity.BaseActivity;

import dagger.Subcomponent;

/**
 * Created by lan on 2016/6/29.
 */
@ActivityScope
@Subcomponent(modules = ActModule.class)
public interface ActComponent {

    void inject(BaseActivity basePresenter);

}
