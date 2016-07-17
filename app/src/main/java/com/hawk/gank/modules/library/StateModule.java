package com.hawk.gank.modules.library;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by heyong on 16/7/16.
 */
@Module
public class StateModule {

    @Provides @Singleton
    public CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }

}
