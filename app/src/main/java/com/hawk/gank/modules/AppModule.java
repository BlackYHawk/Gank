package com.hawk.gank.modules;

import android.app.Application;
import android.content.Context;

import com.hawk.gank.qualifiers.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lan on 2016/4/21.
 */
@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ApplicationContext
    public Context provideApplicationContext() {
        return application;
    }

}
