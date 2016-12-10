package com.hawk.gank.model.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.Override;
import java.lang.String;
import org.threeten.bp.ZonedDateTime;

final class AutoValue_GankCollect extends $AutoValue_GankCollect {
  public static final Parcelable.Creator<AutoValue_GankCollect> CREATOR = new Parcelable.Creator<AutoValue_GankCollect>() {
    @Override
    public AutoValue_GankCollect createFromParcel(Parcel in) {
      return new AutoValue_GankCollect(
          in.readString(),
          (ZonedDateTime) in.readSerializable()
      );
    }
    @Override
    public AutoValue_GankCollect[] newArray(int size) {
      return new AutoValue_GankCollect[size];
    }
  };

  AutoValue_GankCollect(String _id, ZonedDateTime collectedAt) {
    super(_id, collectedAt);
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(_id());
    dest.writeSerializable(collectedAt());
  }

  @Override
  public int describeContents() {
    return 0;
  }
}
