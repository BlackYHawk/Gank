package com.hawk.gank.model.error;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.adapter.rxjava.HttpException;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by heyong on 2016/11/22.
 */
@Singleton
public class RxErrorProcessor implements Action1<Throwable> {

    private final Gson mGson;
    private static final String SOCKET_TIMEOUT = "无法连接服务器";

    @Inject
    RxErrorProcessor(final Gson gson) {
        mGson = gson;
    }

    @Override
    public void call(Throwable throwable) {
        Timber.e(throwable, "RxNetErrorProcessor");
    }

    public void tryWithRxError(final Throwable throwable, final Action1<RxError> handler) {
        if (throwable instanceof HttpException) {
            final HttpException exception = (HttpException) throwable;
            try {
                final String errorBody = exception.response().errorBody().string();
                final RxError rxError = mGson.fromJson(errorBody, RxError.class);
                if (!TextUtils.isEmpty(rxError.message())) {
                    handler.call(rxError);
                }
            } catch (IOException e) {
                RxError rxError = RxError.builder().message(e.getMessage()).build();
                handler.call(rxError);
            }
        }
        else if (throwable instanceof SocketTimeoutException) {
            RxError rxError = RxError.builder().message(SOCKET_TIMEOUT).build();
            handler.call(rxError);
        }
        else {
            RxError rxError = RxError.builder().message(throwable.getMessage()).build();
            handler.call(rxError);
        }
    }
}
