package com.hawk.gank.lib.module.library;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by heyong on 16/7/16.
 */
@Module
public class StateModule {

    @Provides
    public CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }

}
