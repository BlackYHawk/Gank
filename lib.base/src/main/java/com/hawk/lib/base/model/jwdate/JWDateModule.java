package com.hawk.lib.base.model.jwdate;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by heyong on 2016/11/4.
 */
@Module
public class JWDateModule {

    @Singleton
    @Provides
    ThreeTenABPDelegate provideThreeTenABPDelegate(
            @NonNull final ThreeTenABPDelegateImpl delegate) {
        return delegate;
    }

}
