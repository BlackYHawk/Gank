
package com.hawk.gank.model.bean;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import javax.annotation.Generated;
import org.threeten.bp.ZonedDateTime;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 abstract class $$AutoValue_Gank extends Gank {

  private final String _id;
  private final String type;
  private final String url;
  private final List<String> images;
  private final ZonedDateTime createdAt;
  private final ZonedDateTime publishedAt;
  private final String description;

  $$AutoValue_Gank(
      String _id,
      String type,
      String url,
      @Nullable List<String> images,
      ZonedDateTime createdAt,
      ZonedDateTime publishedAt,
      String description) {
    if (_id == null) {
      throw new NullPointerException("Null _id");
    }
    this._id = _id;
    if (type == null) {
      throw new NullPointerException("Null type");
    }
    this.type = type;
    if (url == null) {
      throw new NullPointerException("Null url");
    }
    this.url = url;
    this.images = images;
    if (createdAt == null) {
      throw new NullPointerException("Null createdAt");
    }
    this.createdAt = createdAt;
    if (publishedAt == null) {
      throw new NullPointerException("Null publishedAt");
    }
    this.publishedAt = publishedAt;
    if (description == null) {
      throw new NullPointerException("Null description");
    }
    this.description = description;
  }

  @NonNull
  @Override
  public String _id() {
    return _id;
  }

  @NonNull
  @Override
  public String type() {
    return type;
  }

  @NonNull
  @Override
  public String url() {
    return url;
  }

  @Nullable
  @Override
  public List<String> images() {
    return images;
  }

  @NonNull
  @Override
  public ZonedDateTime createdAt() {
    return createdAt;
  }

  @NonNull
  @Override
  public ZonedDateTime publishedAt() {
    return publishedAt;
  }

  @SerializedName(value = "desc")
  @Override
  public String description() {
    return description;
  }

  @Override
  public String toString() {
    return "Gank{"
        + "_id=" + _id + ", "
        + "type=" + type + ", "
        + "url=" + url + ", "
        + "images=" + images + ", "
        + "createdAt=" + createdAt + ", "
        + "publishedAt=" + publishedAt + ", "
        + "description=" + description
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Gank) {
      Gank that = (Gank) o;
      return (this._id.equals(that._id()))
           && (this.type.equals(that.type()))
           && (this.url.equals(that.url()))
           && ((this.images == null) ? (that.images() == null) : this.images.equals(that.images()))
           && (this.createdAt.equals(that.createdAt()))
           && (this.publishedAt.equals(that.publishedAt()))
           && (this.description.equals(that.description()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this._id.hashCode();
    h *= 1000003;
    h ^= this.type.hashCode();
    h *= 1000003;
    h ^= this.url.hashCode();
    h *= 1000003;
    h ^= (images == null) ? 0 : this.images.hashCode();
    h *= 1000003;
    h ^= this.createdAt.hashCode();
    h *= 1000003;
    h ^= this.publishedAt.hashCode();
    h *= 1000003;
    h ^= this.description.hashCode();
    return h;
  }

  static final class Builder extends Gank.Builder {
    private String _id;
    private String type;
    private String url;
    private List<String> images;
    private ZonedDateTime createdAt;
    private ZonedDateTime publishedAt;
    private String description;
    Builder() {
    }
    Builder(Gank source) {
      this._id = source._id();
      this.type = source.type();
      this.url = source.url();
      this.images = source.images();
      this.createdAt = source.createdAt();
      this.publishedAt = source.publishedAt();
      this.description = source.description();
    }
    @Override
    public Gank.Builder _id(String _id) {
      this._id = _id;
      return this;
    }
    @Override
    public Gank.Builder type(String type) {
      this.type = type;
      return this;
    }
    @Override
    public Gank.Builder url(String url) {
      this.url = url;
      return this;
    }
    @Override
    public Gank.Builder images(@Nullable List<String> images) {
      this.images = images;
      return this;
    }
    @Override
    public Gank.Builder createdAt(ZonedDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }
    @Override
    public Gank.Builder publishedAt(ZonedDateTime publishedAt) {
      this.publishedAt = publishedAt;
      return this;
    }
    @Override
    public Gank.Builder description(String description) {
      this.description = description;
      return this;
    }
    @Override
    public Gank build() {
      String missing = "";
      if (_id == null) {
        missing += " _id";
      }
      if (type == null) {
        missing += " type";
      }
      if (url == null) {
        missing += " url";
      }
      if (createdAt == null) {
        missing += " createdAt";
      }
      if (publishedAt == null) {
        missing += " publishedAt";
      }
      if (description == null) {
        missing += " description";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_Gank(
          this._id,
          this.type,
          this.url,
          this.images,
          this.createdAt,
          this.publishedAt,
          this.description);
    }
  }

}
