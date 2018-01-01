/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.hawk.lib.base.model.provider;

import android.support.v4.util.ArrayMap;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hawk.lib.base.model.jwdate.ZonedDateTimeJsonConverter;
import com.squareup.otto.Bus;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/6.
 */
@Module
public class ProviderModule {

    private final int CONNECT_TIME_OUT = 3;
    private final int SOCKET_TIME_OUT = 3;

    @Singleton
    @Provides
    Bus provideRxBus() {
        return new Bus();
    }

    @Singleton
    @Provides
    Gson provideGson(final GsonConfig config) {
        final GsonBuilder builder = new GsonBuilder();

        if (config.autoGsonTypeAdapterFactory() != null) {
            builder.registerTypeAdapterFactory(config.autoGsonTypeAdapterFactory());
        }
        return builder.registerTypeAdapter(ZonedDateTime.class,
                new ZonedDateTimeJsonConverter(config.dateTimeFormatter()))
                .setDateFormat(config.dateFormatString())
                .setPrettyPrinting()
                .create();
    }

    @Singleton
    @Provides
    OkHttpClient provideHttpClient(final HttpClientConfig config) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();

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

        if (config.enableLog()) {
            builder.addNetworkInterceptor(new StethoInterceptor())
                    .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(final String message) {
                            Timber.tag("OkHttp").d(message);
                        }
                    }).setLevel(HttpLoggingInterceptor.Level.BODY));
        }

        return builder.build();
    }

    @Singleton
    @Provides
    BriteDatabase provideBriteDb(final BriteDbConfig config) {
        final SqlBrite sqlBrite = new SqlBrite.Builder()
                .logger(new SqlBrite.Logger() {
                    @Override
                    public void log(final String message) {
                        Timber.d(message);
                    }
                })
                .build();
        final BriteDatabase briteDb = sqlBrite.wrapDatabaseHelper(config.sqliteOpenHelper(),
                rx.schedulers.Schedulers.io());
        briteDb.setLoggingEnabled(config.enableLogging());
        return briteDb;
    }

}
