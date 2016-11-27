package com.hawk.gank.model.gank;

import com.hawk.gank.model.db.GankDbDelegate;
import com.hawk.gank.model.db.impl.GankDbDelegateImpl;
import com.hawk.gank.model.state.GankState;
import com.hawk.gank.model.state.impl.GankStateImpl;
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

    @ActivityScope
    @Provides
    GankDbDelegate provideGankDb(final GankDbDelegateImpl dbGankDelegate) {
        return dbGankDelegate;
    }

    @ActivityScope
    @Provides
    GankState provideGankState(final GankStateImpl gankState) {
        return gankState;
    }

}
