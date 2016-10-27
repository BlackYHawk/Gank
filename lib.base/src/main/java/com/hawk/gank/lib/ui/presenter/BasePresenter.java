package com.hawk.gank.lib.ui.presenter;

import com.hawk.gank.lib.interfaces.Logger;
import com.hawk.gank.lib.state.BaseState;
import com.hawk.gank.lib.ui.callback.BaseCallback;
import com.hawk.gank.lib.ui.display.BaseDisplay;
import com.hawk.gank.lib.util.Preconditions;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by heyong on 2016/10/1.
 */

public abstract class BasePresenter<U extends BaseCallback<T>, T> extends AbstactPresenter {

    @Inject Logger logger;
    @Inject CompositeSubscription mSubscription;

    private final Set<U> mUis;
    private final Set<U> mUnmodifiableUis;


    public BasePresenter() {
        mUis = new CopyOnWriteArraySet<>();
        mUnmodifiableUis = Collections.unmodifiableSet(mUis);
    }


    public synchronized final void attachUi(U ui) {
        Preconditions.checkArgument(ui != null, "ui cannot be null");
        Preconditions.checkState(!mUis.contains(ui), "UI is already attached");

        mUis.add(ui);

        ui.setCallback(createUiCallbacks(ui));

        if (isInited()) {
            if (!ui.isModal()) {
                updateDisplayTitle(getUiTitle(ui));
            }

            onUiAttached(ui);
            populateUi(ui);
        }
    }

    protected final void updateDisplayTitle(String title) {
        BaseDisplay display = getDisplay();
        if (display != null) {
            display.setActionBarTitle(title);
        }
    }

    protected final void updateDisplayTitle(U ui) {
        updateDisplayTitle(getUiTitle(ui));
    }

    protected String getUiTitle(U ui) {
        return null;
    }

    public synchronized final void detachUi(U ui) {
        Preconditions.checkArgument(ui != null, "ui cannot be null");
        Preconditions.checkState(mUis.contains(ui), "ui is not attached");
        onUiDetached(ui);
        ui.setCallback(null);

        mUis.remove(ui);
    }

    protected final Set<U> getUis() {
        return mUnmodifiableUis;
    }

    @Override
    protected void onInited() {
        if (!mUis.isEmpty()) {
            for (U ui : mUis) {
                onUiAttached(ui);
                populateUi(ui);
            }
        }
    }

    @Override
    protected void onSuspended() {
        if (this.mSubscription != null) {
            this.mSubscription.unsubscribe();
        }
    }

    protected void onUiAttached(U ui) {
    }

    protected void onUiDetached(U ui) {
    }

    protected synchronized final void populateUis() {
        if (logger != null) {
            logger.d(getClass().getSimpleName(), "populateUis");
        }
        for (U ui : mUis) {
            populateUi(ui);
        }
    }

    protected void populateUi(U ui) {
    }

    protected abstract T createUiCallbacks(U ui);

    protected int getId(U ui) {
        return ui.hashCode();
    }

    protected synchronized U findUi(final int id) {
        for (U ui : mUis) {
            if (getId(ui) == id) {
                return ui;
            }
        }
        return null;
    }

    protected final void populateUiFromEvent(BaseState.UiCausedEvent event) {
        Preconditions.checkNotNull(event, "event cannot be null");

        final U ui = findUi(event.callingId);
        if (ui != null) {
            populateUi(ui);
        }
    }

    protected void addSubscription(Subscription subscription) {
        mSubscription.add(subscription);
    }

}
