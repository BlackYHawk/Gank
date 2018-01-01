package com.hawk.gank.model.module;

import com.google.gson.Gson;
import com.hawk.gank.model.http.OpenEyeIO;
import com.hawk.gank.model.state.OpenEyeState;
import com.hawk.gank.model.state.impl.OpenEyeStateImpl;
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
 * Created by heyong on 2018/1/1.
 */
@Module
public class OpenEyeModule {

    @ActivityScope
    @Provides
    OpenEyeIO provideOpenEyeIO(final OkHttpClient okHttpClient,
                            final Gson gson) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constant.EYE_DAILY_SITE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
        return retrofit.create(OpenEyeIO.class);
    }

    @ActivityScope
    @Provides
    OpenEyeState provideOpenEyeState(final OpenEyeStateImpl openEyeState) {
        return openEyeState;
    }

}
