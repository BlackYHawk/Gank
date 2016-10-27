package com.hawk.gank.lib.module;


import com.hawk.gank.lib.qualifiers.ActivityScope;
import com.hawk.gank.lib.ui.activity.BaseActivity;

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
