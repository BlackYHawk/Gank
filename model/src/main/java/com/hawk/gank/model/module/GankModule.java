package com.hawk.gank.model.module;

import com.google.gson.Gson;
import com.hawk.gank.model.db.GankDbDelegate;
import com.hawk.gank.model.db.impl.GankDbDelegateImpl;
import com.hawk.gank.model.http.GankIO;
import com.hawk.gank.model.state.GankState;
import com.hawk.gank.model.state.impl.GankStateImpl;
import com.hawk.gank.model.utils.Constant;
import com.hawk.lib.mvp.qualifiers.ActivityScope;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by heyong on 2016/11/4.
 */
@Module
public class GankModule {

    @ActivityScope
    @Provides
    GankIO provideGankIO(final OkHttpClient okHttpClient,
                         final Gson gson) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.GANK_SITE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
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
