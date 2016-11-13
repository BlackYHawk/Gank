package com.hawk.gank.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lan on 2016/4/21.
 */
@Module
public class AppModule {
    private final Application application;

    public AppModule(final Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Application provideApplicationContext() {
        return application;
    }

}
