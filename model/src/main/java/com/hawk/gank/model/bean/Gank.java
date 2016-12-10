package com.hawk.gank.model.bean;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.hawk.gank.model.GankModel;
import com.hawk.gank.model.adapter.ListToStringAdapter;
import com.hawk.gank.model.adapter.ZonedDateTimeDelightAdapter;
import com.hawk.gank.model.provider.DateTimeFormatterProvider;
import com.hawk.lib.base.model.type.ListItem;
import com.squareup.sqldelight.RowMapper;

import org.threeten.bp.ZonedDateTime;

import java.util.List;


/**
 * Created by lan on 2016/10/31.
 */
@AutoValue
public abstract class Gank implements GankModel, Parcelable, ListItem<Gank> {

    public abstract @SerializedName("desc") String description();

    public static final Factory<Gank> FACTORY = new Factory<>(new GankModel.Creator<Gank>() {
        @Override
        public Gank create(@Nullable String _id, @NonNull String description, @NonNull String type,
                           @NonNull String url, List<String> images, @NonNull ZonedDateTime createdAt, @NonNull ZonedDateTime publishedAt) {
            return new AutoValue_Gank(_id, type, url, images, createdAt, publishedAt, description);
        }
    }, new ListToStringAdapter(), new ZonedDateTimeDelightAdapter(DateTimeFormatterProvider.provideDateTimeFormatter()),
            new ZonedDateTimeDelightAdapter(DateTimeFormatterProvider.provideDateTimeFormatter()));

    public static final RowMapper<Gank> MAPPER = FACTORY.select_pageMapper();

    public static TypeAdapter<Gank> typeAdapter(final Gson gson) {
        return new AutoValue_Gank.GsonTypeAdapter(gson);
    }

    @NonNull
    public static Builder builder() {
        return new AutoValue_Gank.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder _id(@Nullable String _id);

        public abstract Builder description(@Nullable String description);

        public abstract Builder type(@Nullable String type);

        public abstract Builder url(@Nullable String url);

        public abstract Builder images(@Nullable List<String> images);

        public abstract Builder createdAt(@Nullable ZonedDateTime createdAt);

        public abstract Builder publishedAt(@Nullable ZonedDateTime publishedAt);

        public abstract Gank build();
    }

}
