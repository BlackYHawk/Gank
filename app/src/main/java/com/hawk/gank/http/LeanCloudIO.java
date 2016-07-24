package com.hawk.gank.http;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.hawk.gank.data.entity.AccountBean;

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
    Observable<AVUser> login(@Query("username") String username, @Query("password") String password);

    @Headers({"Content-Type: application/json"})
    @POST("1.1/users")
    Observable<AVUser> register(@Body AccountBean accountBean);

    @Headers({"Content-Type: image/png"})
    @Multipart
    @POST("1.1/files/{filename}")
    Observable<AVFile> uploadFile(@Path("filename") String filename,
                                  @Part("file\"; filename=\"image.png\"") RequestBody file);

}
