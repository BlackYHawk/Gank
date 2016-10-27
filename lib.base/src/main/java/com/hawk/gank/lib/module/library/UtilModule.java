package com.hawk.gank.lib.module.library;

import android.content.Context;

import com.google.gson.Gson;
import com.hawk.gank.lib.interfaces.Logger;
import com.hawk.gank.lib.interfaces.StringFetcher;
import com.hawk.gank.lib.interfaces.impl.AndroidLogger;
import com.hawk.gank.lib.interfaces.impl.AndroidStringFetcher;
import com.hawk.gank.lib.interfaces.impl.CharacterParser;
import com.hawk.gank.lib.qualifiers.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lan on 2016/4/14.
 */
@Module
public class UtilModule {

    @Provides @Singleton
    public Gson provideGson() {
        return new Gson();
    }

    @Provides @Singleton
    public Logger provideLogger() {
        return new AndroidLogger();
    }

    @Provides @Singleton
    public CharacterParser provideCharacterParser() {
        return new CharacterParser();
    }

    @Provides @Singleton
    public StringFetcher provideStringFetcher(@ApplicationContext Context context) {
        return new AndroidStringFetcher(context);
    }

}
