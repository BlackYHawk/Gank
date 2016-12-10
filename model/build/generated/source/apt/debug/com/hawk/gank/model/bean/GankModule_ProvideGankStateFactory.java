package com.hawk.gank.model.bean;

import com.hawk.gank.model.state.GankState;
import com.hawk.gank.model.state.impl.GankStateImpl;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GankModule_ProvideGankStateFactory implements Factory<GankState> {
  private final GankModule module;

  private final Provider<GankStateImpl> gankStateProvider;

  public GankModule_ProvideGankStateFactory(
      GankModule module, Provider<GankStateImpl> gankStateProvider) {
    assert module != null;
    this.module = module;
    assert gankStateProvider != null;
    this.gankStateProvider = gankStateProvider;
  }

  @Override
  public GankState get() {
    return Preconditions.checkNotNull(
        module.provideGankState(gankStateProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<GankState> create(
      GankModule module, Provider<GankStateImpl> gankStateProvider) {
    return new GankModule_ProvideGankStateFactory(module, gankStateProvider);
  }
}
