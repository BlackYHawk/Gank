package com.hawk.gank.modules.library;

import android.content.Context;

import com.google.gson.Gson;
import com.hawk.gank.interfaces.Logger;
import com.hawk.gank.interfaces.StringFetcher;
import com.hawk.gank.qualifiers.ApplicationContext;
import com.hawk.gank.interfaces.impl.AndroidLogger;
import com.hawk.gank.interfaces.impl.AndroidStringFetcher;
import com.hawk.gank.util.CharacterParser;

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
