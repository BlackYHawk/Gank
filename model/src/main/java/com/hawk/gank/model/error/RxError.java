package com.hawk.gank.model.error;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

/**
 * Created by heyong on 2016/11/22.
 */
@AutoValue
public abstract class RxError implements Parcelable {

    public abstract String message();

    @Nullable
    public abstract String documentation_url();

    @Nullable
    public abstract List<Detail> errors();

    public static TypeAdapter<RxError> typeAdapter(final Gson gson) {
        return new AutoValue_RxError.GsonTypeAdapter(gson);
    }

    @AutoValue
    public abstract static class Detail implements Parcelable {

        public abstract String resource();

        public abstract String field();

        public abstract String code();

        public static TypeAdapter<Detail> typeAdapter(final Gson gson) {
            return new AutoValue_RxError_Detail.GsonTypeAdapter(gson);
        }
    }

    @NonNull
    public static RxError.Builder builder() {
        return new AutoValue_RxError.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder message(@NonNull String message);

        public abstract Builder documentation_url(@Nullable String documentation_url);

        public abstract Builder errors(@Nullable List<Detail> errors);

        public abstract RxError build();
    }

}
