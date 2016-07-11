package com.hawk.gank.http;

import com.google.android.agera.Result;
import com.google.android.agera.Supplier;
import com.hawk.gank.data.GankData;
import com.hawk.gank.util.Constant;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by heyong on 16/7/10.
 */
public interface GankIO {

    @GET("data/福利/" + Constant.GANK_PAGE + "/{page}")
    Supplier<Result<GankData>> getMMData(@Path("page") int page);

    @GET("data/Android/" + Constant.GANK_PAGE + "/{page}")
    Supplier<Result<GankData>> getAndroidData(@Path("page") int page);

    @GET("data/iOS/" + Constant.GANK_PAGE + "/{page}")
    Supplier<Result<GankData>> getIOSData(@Path("page") int page);

}
