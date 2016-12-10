package com.hawk.gank.model.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.List;
import org.threeten.bp.ZonedDateTime;

final class AutoValue_Gank extends $AutoValue_Gank {
  public static final Parcelable.Creator<AutoValue_Gank> CREATOR = new Parcelable.Creator<AutoValue_Gank>() {
    @Override
    @SuppressWarnings("unchecked")
    public AutoValue_Gank createFromParcel(Parcel in) {
      return new AutoValue_Gank(
          in.readString(),
          in.readString(),
          in.readString(),
          (List<String>) in.readArrayList(String.class.getClassLoader()),
          (ZonedDateTime) in.readSerializable(),
          (ZonedDateTime) in.readSerializable(),
          in.readString()
      );
    }
    @Override
    public AutoValue_Gank[] newArray(int size) {
      return new AutoValue_Gank[size];
    }
  };

  AutoValue_Gank(String _id, String type, String url, List<String> images, ZonedDateTime createdAt, ZonedDateTime publishedAt, String description) {
    super(_id, type, url, images, createdAt, publishedAt, description);
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(_id());
    dest.writeString(type());
    dest.writeString(url());
    dest.writeList(images());
    dest.writeSerializable(createdAt());
    dest.writeSerializable(publishedAt());
    dest.writeString(description());
  }

  @Override
  public int describeContents() {
    return 0;
  }
}
