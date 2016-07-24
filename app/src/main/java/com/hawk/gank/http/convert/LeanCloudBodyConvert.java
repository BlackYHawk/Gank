package com.hawk.gank.http.convert;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by heyong on 16/7/24.
 */
public class LeanCloudBodyConvert<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    public LeanCloudBodyConvert(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody body) throws IOException {
        String value = body.string();

        Log.e("response", "value:" + value);
        Result<T> result = null;
        //只解析result字段
        try {
            result = gson.fromJson(value, Result.class);

            throw new ResultException(result.getCode(), result.getError());
        } catch (JsonSyntaxException e) {
            //表示成功返回，继续用本来的type解析
            T object = gson.fromJson(value, type);

            return object;
        } finally {

        }
    }
}