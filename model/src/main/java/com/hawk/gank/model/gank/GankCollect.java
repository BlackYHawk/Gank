package com.hawk.gank.model.gank;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.hawk.gank.model.Gank_CollectModel;
import com.hawk.gank.model.adapter.ZonedDateTimeDelightAdapter;
import com.hawk.gank.model.provider.DateTimeFormatterProvider;
import com.squareup.sqldelight.RowMapper;

import org.threeten.bp.ZonedDateTime;

/**
 * Created by heyong on 2016/11/27.
 */
@AutoValue
public abstract class GankCollect implements Gank_CollectModel, Parcelable {

    public static final Gank_CollectModel.Factory<GankCollect> FACTORY = new Gank_CollectModel.Factory<>(new Gank_CollectModel.Creator<GankCollect>() {
        @Override
        public GankCollect create(@NonNull String _id, @NonNull ZonedDateTime collectedAt) {
            return new AutoValue_GankCollect(_id, collectedAt);
        }
    }, new ZonedDateTimeDelightAdapter(DateTimeFormatterProvider.provideDateTimeFormatter()));

    public static final RowMapper<GankCollect> MAPPER = FACTORY.select_pageMapper();

    public static TypeAdapter<GankCollect> typeAdapter(final Gson gson) {
        return new AutoValue_GankCollect.GsonTypeAdapter(gson);
    }

    @NonNull
    public static Builder builder() {
        return new AutoValue_GankCollect.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder _id(@Nullable String _id);

        public abstract Builder collectedAt(@Nullable ZonedDateTime collectedAt);

        public abstract GankCollect build();
    }

}
