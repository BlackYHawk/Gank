package com.hawk.gank.model.gank;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by heyong on 16/7/10.
 */
public interface GankIO {
    int PAGE_SIZE = 10;

    @GET("data/Android/" + PAGE_SIZE + "/{page}")
    Observable<GankResult> getAndroidData(@Path("page") int page);

    @GET("data/iOS/" + PAGE_SIZE + "/{page}")
    Observable<GankResult> getIosData(@Path("page") int page);

    @GET("data/福利/" + PAGE_SIZE + "/{page}")
    Observable<GankResult> getMMData(@Path("page") int page);

    @GET("data/前端/" + PAGE_SIZE + "/{page}")
    Observable<GankResult> getFrontData(@Path("page") int page);

    @GET("data/拓展资源/" + PAGE_SIZE + "/{page}")
    Observable<GankResult> getExpandData(@Path("page") int page);

    @GET("data/休息视频/" + PAGE_SIZE + "/{page}")
    Observable<GankResult> getVideoData(@Path("page") int page);

}
