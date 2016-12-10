package com.hawk.gank.model.bean;

import com.hawk.gank.model.db.GankDbDelegate;
import com.hawk.gank.model.error.RxErrorProcessor;
import com.hawk.gank.model.http.GankIO;
import com.hawk.gank.model.repository.GankRepo;
import com.hawk.gank.model.state.GankState;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GankRepo_Factory implements Factory<GankRepo> {
  private final Provider<GankIO> gankIOProvider;

  private final Provider<GankDbDelegate> gankDbProvider;

  private final Provider<GankState> gankStateProvider;

  private final Provider<RxErrorProcessor> rxErrorProcessorProvider;

  public GankRepo_Factory(
      Provider<GankIO> gankIOProvider,
      Provider<GankDbDelegate> gankDbProvider,
      Provider<GankState> gankStateProvider,
      Provider<RxErrorProcessor> rxErrorProcessorProvider) {
    assert gankIOProvider != null;
    this.gankIOProvider = gankIOProvider;
    assert gankDbProvider != null;
    this.gankDbProvider = gankDbProvider;
    assert gankStateProvider != null;
    this.gankStateProvider = gankStateProvider;
    assert rxErrorProcessorProvider != null;
    this.rxErrorProcessorProvider = rxErrorProcessorProvider;
  }

  @Override
  public GankRepo get() {
    return new GankRepo(
        gankIOProvider.get(),
        gankDbProvider.get(),
        gankStateProvider.get(),
        rxErrorProcessorProvider.get());
  }

  public static Factory<GankRepo> create(
      Provider<GankIO> gankIOProvider,
      Provider<GankDbDelegate> gankDbProvider,
      Provider<GankState> gankStateProvider,
      Provider<RxErrorProcessor> rxErrorProcessorProvider) {
    return new GankRepo_Factory(
        gankIOProvider, gankDbProvider, gankStateProvider, rxErrorProcessorProvider);
  }
}
