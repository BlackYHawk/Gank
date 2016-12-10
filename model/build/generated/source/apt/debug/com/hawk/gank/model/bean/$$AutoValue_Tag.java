
package com.hawk.gank.model.bean;

import android.support.annotation.NonNull;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 abstract class $$AutoValue_Tag extends Tag {

  private final String type;
  private final boolean valid;

  $$AutoValue_Tag(
      String type,
      boolean valid) {
    if (type == null) {
      throw new NullPointerException("Null type");
    }
    this.type = type;
    this.valid = valid;
  }

  @NonNull
  @Override
  public String type() {
    return type;
  }

  @Override
  public boolean valid() {
    return valid;
  }

  @Override
  public String toString() {
    return "Tag{"
        + "type=" + type + ", "
        + "valid=" + valid
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof Tag) {
      Tag that = (Tag) o;
      return (this.type.equals(that.type()))
           && (this.valid == that.valid());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.type.hashCode();
    h *= 1000003;
    h ^= this.valid ? 1231 : 1237;
    return h;
  }

  static final class Builder extends Tag.Builder {
    private String type;
    private Boolean valid;
    Builder() {
    }
    Builder(Tag source) {
      this.type = source.type();
      this.valid = source.valid();
    }
    @Override
    public Tag.Builder type(String type) {
      this.type = type;
      return this;
    }
    @Override
    public Tag.Builder valid(boolean valid) {
      this.valid = valid;
      return this;
    }
    @Override
    public Tag build() {
      String missing = "";
      if (type == null) {
        missing += " type";
      }
      if (valid == null) {
        missing += " valid";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new AutoValue_Tag(
          this.type,
          this.valid);
    }
  }

}
