
package com.hawk.gank.model.bean;

import java.util.List;
import javax.annotation.Generated;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 abstract class $$AutoValue_GankResult extends GankResult {

  private final boolean error;
  private final List<Gank> results;

  $$AutoValue_GankResult(
      boolean error,
      List<Gank> results) {
    this.error = error;
    if (results == null) {
      throw new NullPointerException("Null results");
    }
    this.results = results;
  }

  @Override
  public boolean error() {
    return error;
  }

  @Override
  public List<Gank> results() {
    return results;
  }

  @Override
  public String toString() {
    return "GankResult{"
        + "error=" + error + ", "
        + "results=" + results
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof GankResult) {
      GankResult that = (GankResult) o;
      return (this.error == that.error())
           && (this.results.equals(that.results()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= this.error ? 1231 : 1237;
    h *= 1000003;
    h ^= this.results.hashCode();
    return h;
  }

}
