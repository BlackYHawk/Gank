package com.hawk.gank.http.convert;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by heyong on 16/7/24.
 */
public class LeanCloudConvertFactory extends Converter.Factory {

    public static LeanCloudConvertFactory create() {
        return create(new Gson());
    }

    public static LeanCloudConvertFactory create(Gson gson) {
        return new LeanCloudConvertFactory(gson);
    }

    private final Gson gson;

    private LeanCloudConvertFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new LeanCloudBodyConvert<>(gson, type);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

}
