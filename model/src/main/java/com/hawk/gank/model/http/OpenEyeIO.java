package com.hawk.gank.model.http;

import com.hawk.gank.model.bean.EyeData;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by heyong on 16/7/18.
 */
public interface OpenEyeIO {
    int PAGE_NUM = 1;

    @GET("feed?num=" + PAGE_NUM)
    Flowable<EyeData> getDailyData(@Query("date") String date);

}
