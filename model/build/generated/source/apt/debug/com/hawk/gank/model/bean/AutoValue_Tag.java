package com.hawk.gank.model.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.Override;
import java.lang.String;

final class AutoValue_Tag extends $AutoValue_Tag {
  public static final Parcelable.Creator<AutoValue_Tag> CREATOR = new Parcelable.Creator<AutoValue_Tag>() {
    @Override
    public AutoValue_Tag createFromParcel(Parcel in) {
      return new AutoValue_Tag(
          in.readString(),
          in.readInt() == 1
      );
    }
    @Override
    public AutoValue_Tag[] newArray(int size) {
      return new AutoValue_Tag[size];
    }
  };

  AutoValue_Tag(String type, boolean valid) {
    super(type, valid);
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(type());
    dest.writeInt(valid() ? 1 : 0);
  }

  @Override
  public int describeContents() {
    return 0;
  }
}
