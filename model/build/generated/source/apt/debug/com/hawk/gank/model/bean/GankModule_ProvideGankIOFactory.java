package com.hawk.gank.model.bean;

import com.hawk.gank.model.http.GankIO;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GankModule_ProvideGankIOFactory implements Factory<GankIO> {
  private final GankModule module;

  private final Provider<Retrofit> retrofitProvider;

  public GankModule_ProvideGankIOFactory(GankModule module, Provider<Retrofit> retrofitProvider) {
    assert module != null;
    this.module = module;
    assert retrofitProvider != null;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public GankIO get() {
    return Preconditions.checkNotNull(
        module.provideGankIO(retrofitProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<GankIO> create(GankModule module, Provider<Retrofit> retrofitProvider) {
    return new GankModule_ProvideGankIOFactory(module, retrofitProvider);
  }
}
