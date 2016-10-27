package com.hawk.gank.lib.module.library;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.hawk.gank.lib.qualifiers.ApplicationContext;
import com.hawk.gank.lib.util.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by lan on 2016/4/21.
 */
@Module
public class NetworkModule {

    private final int CONNECT_TIME_OUT = 3;
    private final int SOCKET_TIME_OUT = 3;
    private final int cachSize = 10*1024*1024;

    @Provides @Singleton
    public OkHttpClient provideOkHttpClient(@ApplicationContext Context context) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
        builder.readTimeout(SOCKET_TIME_OUT, TimeUnit.SECONDS);
        builder.writeTimeout(SOCKET_TIME_OUT, TimeUnit.SECONDS);

        builder.cookieJar(new CookieJar() {
            private final ArrayMap<HttpUrl, List<Cookie>> cookieStore = new ArrayMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url, cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });

        File cacheDirectory = new File(context.getCacheDir(), Constant.DEFAULT_CACHE_DIR);
        Cache cache = new Cache(cacheDirectory, cachSize);
        builder.cache(cache);

        return builder.build();
    }

}
