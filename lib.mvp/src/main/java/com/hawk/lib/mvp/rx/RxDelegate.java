package com.hawk.lib.mvp.rx;

import com.hawk.lib.mvp.util.Preconditions;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lan on 2016/10/27.
 */

public class RxDelegate {
    private CompositeSubscription mSubscriptions2Stop;
    private CompositeSubscription mSubscriptions2Destroy;

    public synchronized boolean addUtilStop(Subscription subscription) {
        Preconditions.checkState(mSubscriptions2Stop != null, "addUtilStop should be called between onStart and onStop");
        mSubscriptions2Stop.add(subscription);
        return true;
    }

    public synchronized boolean addUtilDestroy(Subscription subscription) {
        Preconditions.checkState(mSubscriptions2Destroy != null, "addUtilDestroy should be called between onCreate and onDestroy");
        mSubscriptions2Destroy.add(subscription);
        return true;
    }

    public synchronized void remove(Subscription subscription) {
        Preconditions.checkState(mSubscriptions2Stop != null && mSubscriptions2Destroy != null, "remove should not be called after onDestroy");
        if (mSubscriptions2Stop != null) {
            mSubscriptions2Stop.remove(subscription);
        }
        if (mSubscriptions2Destroy != null) {
            mSubscriptions2Destroy.remove(subscription);
        }
    }

    public synchronized void onCreate() {
        Preconditions.checkState(mSubscriptions2Destroy == null, "onCreate called multiple times");
        mSubscriptions2Destroy = new CompositeSubscription();
    }

    public synchronized void onStart() {
        Preconditions.checkState(mSubscriptions2Stop == null, "onStart called multiple times");
        mSubscriptions2Stop = new CompositeSubscription();
    }

    public synchronized void onStop() {
        Preconditions.checkState(mSubscriptions2Stop != null, "onStop called multiple times or onStart not called");
        mSubscriptions2Stop.unsubscribe();
        mSubscriptions2Stop = null;
    }

    public synchronized void onDestroy() {
        Preconditions.checkState(mSubscriptions2Destroy != null, "onDestroy called multiple times or onCreate not called");
        mSubscriptions2Destroy.unsubscribe();
        mSubscriptions2Destroy = null;
    }

}
