package com.hawk.gank.modules.library;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lan on 2016/4/21.
 */
public class NetworkModule {

    public OkHttpClient provideLeanCloudOkHttpClient(OkHttpClient okHttpClient) {
        OkHttpClient.Builder builder = okHttpClient.newBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .header("X-LC-Id", "7ahgYGrjijmpfhgrTa4s0jX0-gzGzoHsz")
                                .header("X-LC-Key", "e3LVEnDLFUVcjNKCCE16lxQz")
                                .method(original.method(), original.body())
                                .build();
                        Log.e("URL", "url:" + request.url() + " headers:" + request.headers().toString()
                                + " method:" + request.method());
                        Response response = chain.proceed(request);
                        Log.e("response", "url:" + response.request().url() + " headers:" + response.headers());

                        return response;
                    }
                });

        return builder.build();
    }

}
