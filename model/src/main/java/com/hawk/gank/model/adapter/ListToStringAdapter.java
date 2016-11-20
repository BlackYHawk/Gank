package com.hawk.gank.model.adapter;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.sqldelight.ColumnAdapter;

import java.util.List;

/**
 * Created by heyong on 2016/11/20.
 */

public class ListToStringAdapter implements ColumnAdapter<List<String>, String> {
    private Gson gson;

    public ListToStringAdapter() {
        gson = new Gson();
    }

    @NonNull
    @Override
    public List<String> decode(String databaseValue) {
        List<String> images = gson.fromJson(databaseValue, new TypeToken<List<String>>(){}.getType());
        return images;
    }

    @Override
    public String encode(@NonNull List<String> value) {
        return gson.toJson(value);
    }
}
