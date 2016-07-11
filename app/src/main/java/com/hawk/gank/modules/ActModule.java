package com.hawk.gank.modules;


import com.hawk.gank.qualifiers.ActivityScope;
import com.hawk.gank.ui.activity.base.BaseActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lan on 2016/6/29.
 */
@Module
public class ActModule {
    private final BaseActivity activity;

    public ActModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides @ActivityScope
    public BaseActivity provideActivity() {
        return activity;
    }

}
