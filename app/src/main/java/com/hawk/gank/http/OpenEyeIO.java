package com.hawk.gank.http;

import com.hawk.gank.data.EyeData;
import com.hawk.gank.util.Constant;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by heyong on 16/7/18.
 */
public interface OpenEyeIO {

    @GET("feed?num=" + Constant.EYE_DAILY_NUM)
    Observable<EyeData> getDailyData(@Query("date") String date);

}
