
package com.hawk.gank.model.bean;

import android.support.annotation.NonNull;
import javax.annotation.Generated;
import org.threeten.bp.ZonedDateTime;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 abstract class $$AutoValue_GankCollect extends GankCollect {

  private final String _id;
  private final ZonedDateTime collectedAt;

  $$AutoValue_GankCollect(
      String _id,
      ZonedDateTime collectedAt) {
    if (_id == null) {
      throw new NullPointerException("Null _id");
    }
    this._id = _id;
    if (collectedAt == null) {
      throw new NullPointerException("Null collectedAt");
    }
    this.collectedAt = collectedAt;
  }

  @NonNull
  @Override
  public String _id() {
    return _id;
  }

  @NonNull
  @Override
  public ZonedDateTime collectedAt() {
    return collectedAt;
  }

  @Override
  public String toString() {
    return "GankCollect{"
        + "_id=" + _id + ", "
        + "collectedAt=" + collectedAt
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof GankCollect) {
      GankCollect that = (GankCollect) o;
      return (this._id.equals(that._id()))
           && (this.collectedAt.equals(that.collectedAt()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this._id.hashCode();
    h *= 1000003;
    h ^= this.collectedAt.hashCode();
    return h;
  }

  static final class Builder extends GankCollect.Builder {
    private String _id;
    private ZonedDateTime collectedAt;
    Builder() {
    }
    Builder(GankCollect source) {
      this._id = source._id();
      this.collectedAt = source.collectedAt();
    }
    @Override
    public GankCollect.Builder _id(String _id) {
      this._id = _id;
      return this;
    }
    @Override
    public GankCollect.Builder collectedAt(ZonedDateTime collectedAt) {
      this.collectedAt = collectedAt;
      return this;
    }
    @Override
    public GankCollect build() {
      String missing = "";
      if (_id == null) {
        missing += " _id";
      }
      if (collectedAt == null) {
        missing += " collectedAt";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_GankCollect(
          this._id,
          this.collectedAt);
    }
  }

}
