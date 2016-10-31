package com.hawk.gank.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.hawk.gank.model.adapter.ZonedDateTimeDelightAdapter;
import com.squareup.sqldelight.RowMapper;

import org.threeten.bp.ZonedDateTime;


/**
 * Created by lan on 2016/10/31.
 */
@AutoValue
public abstract class Gank implements GankModel, Parcelable {

    public static final Factory<Gank> FACTORY = new Factory<>(new GankModel.Creator<Gank>() {
        @Override
        public Gank create(@Nullable String _id, @NonNull String description, @NonNull String type, @NonNull String url, @NonNull String images, @NonNull ZonedDateTime createdAt, @NonNull ZonedDateTime publishedAt) {
            return new AutoValue_Gank(_id, description, type, url, images, createdAt, publishedAt);
        }
    }, new ZonedDateTimeDelightAdapter(DateTimeFormatterProvider.provideDateTimeFormatter()), new ZonedDateTimeDelightAdapter(DateTimeFormatterProvider.provideDateTimeFormatter()));

    public static final RowMapper<Gank> MAPPER = FACTORY.select_allMapper();
}
