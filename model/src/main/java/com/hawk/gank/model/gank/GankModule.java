package com.hawk.gank.model.gank;

import com.hawk.lib.mvp.qualifiers.ActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by heyong on 2016/11/4.
 */
@Module
public class GankModule {

    @ActivityScope
    @Provides
    GankIO provideGankIO(final Retrofit retrofit) {
        return retrofit.create(GankIO.class);
    }

}
