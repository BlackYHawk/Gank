package com.hawk.gank.modules.library;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by heyong on 16/7/16.
 */
public class StateModule {

    public CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }

}
