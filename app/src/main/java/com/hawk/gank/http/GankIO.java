package com.hawk.gank.http;

import com.hawk.gank.data.GankData;
import com.hawk.gank.util.Constant;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by heyong on 16/7/10.
 */
public interface GankIO {

    @GET("data/福利/" + Constant.GANK_PAGE + "/{page}")
    Observable<GankData> getMMData(@Path("page") int page);

    @GET("data/Android/" + Constant.GANK_PAGE + "/{page}")
    Observable<GankData> getAndroidData(@Path("page") int page);

    @GET("data/iOS/" + Constant.GANK_PAGE + "/{page}")
    Observable<GankData> getIOSData(@Path("page") int page);

}
