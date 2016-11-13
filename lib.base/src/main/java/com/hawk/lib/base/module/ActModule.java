package com.hawk.lib.base.module;


import android.support.v7.app.AppCompatActivity;
import com.hawk.lib.mvp.qualifiers.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lan on 2016/6/29.
 */
@Module
public class ActModule {
    private final AppCompatActivity activity;

    public ActModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides @ActivityScope
    public AppCompatActivity provideActivity() {
        return activity;
    }

}
