package com.hawk.gank.model.http;

import com.hawk.gank.model.bean.GankResult;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by heyong on 16/7/10.
 */
public interface GankIO {
    int PAGE_SIZE = 10;

    @GET("data/Android/" + PAGE_SIZE + "/{page}")
    Flowable<GankResult> getAndroidData(@Path("page") int page);

    @GET("data/iOS/" + PAGE_SIZE + "/{page}")
    Flowable<GankResult> getIosData(@Path("page") int page);

    @GET("data/福利/" + PAGE_SIZE + "/{page}")
    Flowable<GankResult> getMMData(@Path("page") int page);

    @GET("data/前端/" + PAGE_SIZE + "/{page}")
    Flowable<GankResult> getFrontData(@Path("page") int page);

    @GET("data/拓展资源/" + PAGE_SIZE + "/{page}")
    Flowable<GankResult> getExpandData(@Path("page") int page);

    @GET("data/休息视频/" + PAGE_SIZE + "/{page}")
    Flowable<GankResult> getVideoData(@Path("page") int page);

}
