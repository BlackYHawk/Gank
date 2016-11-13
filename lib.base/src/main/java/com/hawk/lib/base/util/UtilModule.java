package com.hawk.lib.base.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hawk.lib.base.util.impl.CharacterParser;
import com.hawk.lib.base.util.impl.ResParser;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by lan on 2016/4/14.
 */
@Module
public class UtilModule {

    private final Context mContext;

    public UtilModule(@NonNull final Context context) {
        this.mContext = context;
    }

    @Provides @Singleton
    public CharacterDelegate provideCharacterParser() {
        return new CharacterParser();
    }

    @Provides @Singleton
    public ResDelegate provideStringFetcher() {
        return new ResParser(mContext);
    }

}
