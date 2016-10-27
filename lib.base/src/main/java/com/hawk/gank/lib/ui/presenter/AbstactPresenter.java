package com.hawk.gank.lib.ui.presenter;

import android.content.Intent;

import com.hawk.gank.lib.ui.display.BaseDisplay;
import com.hawk.gank.lib.util.Preconditions;


/**
 * Created by heyong on 2016/9/29.
 */

abstract class AbstactPresenter {
    private BaseDisplay mDisplay;
    private boolean mInited;

    public final void init() {
        Preconditions.checkState(!mInited, "Already inited");
        mInited = true;
        onInited();
    }

    public final void suspend() {
        Preconditions.checkState(mInited, "Not inited");
        onSuspended();
        mInited = false;
    }

    public final boolean isInited() {
        return mInited;
    }

    protected void onInited() {}

    protected void onSuspended() {}

    public boolean handleIntent(Intent intent) {
        return false;
    }

    protected void setDisplay(BaseDisplay display) {
        mDisplay = display;
    }

    protected final BaseDisplay getDisplay() {
        return mDisplay;
    }

    protected final void assertInited() {
        Preconditions.checkState(mInited, "Must be init'ed to perform action");
    }

}
