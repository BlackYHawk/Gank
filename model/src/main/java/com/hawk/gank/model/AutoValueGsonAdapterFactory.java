package com.hawk.gank.model;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by heyong on 2016/11/4.
 */
@GsonTypeAdapterFactory
public abstract class AutoValueGsonAdapterFactory implements TypeAdapterFactory {

    public static TypeAdapterFactory create() {
        return new AutoValueGson_AutoValueGsonAdapterFactory();
    }

}
