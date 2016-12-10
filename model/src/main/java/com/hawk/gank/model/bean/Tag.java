package com.hawk.gank.model.bean;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.hawk.gank.model.TagModel;
import com.squareup.sqldelight.RowMapper;

/**
 * Created by heyong on 2016/11/29.
 */
@AutoValue
public abstract class Tag implements TagModel, Parcelable {

    public static final Factory<Tag> FACTORY = new Factory<>(new TagModel.Creator<Tag>() {
        @Override
        public Tag create(@NonNull String type, @NonNull boolean valid) {
            return new AutoValue_Tag(type, valid);
        }
    });

    public static final RowMapper<Tag> MAPPER = FACTORY.select_allMapper();

    public static TypeAdapter<Tag> typeAdapter(final Gson gson) {
        return new AutoValue_Tag.GsonTypeAdapter(gson);
    }

    @NonNull
    public static Builder builder() {
        return new AutoValue_Tag.Builder();
    }

    @NonNull
    public static Builder builder(Tag tag) {
        return new AutoValue_Tag.Builder(tag);
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder type(@NonNull String type);

        public abstract Builder valid(@NonNull boolean valid);

        public abstract Tag build();
    }


}
