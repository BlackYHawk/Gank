package com.hawk.gank.model.bean;

import com.hawk.gank.model.db.GankDbDelegate;
import com.hawk.gank.model.db.impl.GankDbDelegateImpl;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GankModule_ProvideGankDbFactory implements Factory<GankDbDelegate> {
  private final GankModule module;

  private final Provider<GankDbDelegateImpl> dbGankDelegateProvider;

  public GankModule_ProvideGankDbFactory(
      GankModule module, Provider<GankDbDelegateImpl> dbGankDelegateProvider) {
    assert module != null;
    this.module = module;
    assert dbGankDelegateProvider != null;
    this.dbGankDelegateProvider = dbGankDelegateProvider;
  }

  @Override
  public GankDbDelegate get() {
    return Preconditions.checkNotNull(
        module.provideGankDb(dbGankDelegateProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<GankDbDelegate> create(
      GankModule module, Provider<GankDbDelegateImpl> dbGankDelegateProvider) {
    return new GankModule_ProvideGankDbFactory(module, dbGankDelegateProvider);
  }
}
