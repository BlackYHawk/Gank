package com.hawk.gank.http;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.hawk.gank.model.bean.entity.AccountBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by heyong on 16/7/24.
 */
public interface LeanCloudIO {

    @Headers({"Content-Type: application/json"})
    @GET("1.1/login")
    Observable<AccountBean> login(@Query("username") String username, @Query("password") String password);

    @Headers({"Content-Type: application/json"})
    @POST("1.1/users")
    Observable<AccountBean> register(@Body AccountBean accountBean);

    @Headers({"Content-Type: image/png"})
    @Multipart
    @POST("1.1/files/{filename}")
    Observable<AVFile> uploadFile(@Path("filename") String filename,
                                  @Part("file\"; filename=\"image.png\"") RequestBody file);

    @Headers({"Content-Type: application/json"})
    @GET("1.1/classes/Post/{objectId}")
    Observable<AVObject> getFile(@Path("objectId") String objectId);

}
