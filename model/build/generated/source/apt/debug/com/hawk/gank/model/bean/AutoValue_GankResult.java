package com.hawk.gank.model.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.Override;
import java.lang.SuppressWarnings;
import java.util.List;

final class AutoValue_GankResult extends $AutoValue_GankResult {
  public static final Parcelable.Creator<AutoValue_GankResult> CREATOR = new Parcelable.Creator<AutoValue_GankResult>() {
    @Override
    @SuppressWarnings("unchecked")
    public AutoValue_GankResult createFromParcel(Parcel in) {
      return new AutoValue_GankResult(
          in.readInt() == 1,
          (List<Gank>) in.readArrayList(Gank.class.getClassLoader())
      );
    }
    @Override
    public AutoValue_GankResult[] newArray(int size) {
      return new AutoValue_GankResult[size];
    }
  };

  AutoValue_GankResult(boolean error, List<Gank> results) {
    super(error, results);
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(error() ? 1 : 0);
    dest.writeList(results());
  }

  @Override
  public int describeContents() {
    return 0;
  }
}
