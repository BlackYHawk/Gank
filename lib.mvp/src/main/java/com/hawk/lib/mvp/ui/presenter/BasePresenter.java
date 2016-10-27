package com.hawk.lib.mvp.ui.presenter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import com.hawk.lib.mvp.ui.display.BaseDisplay;
import com.hawk.lib.mvp.util.Preconditions;


/**
 * Created by heyong on 2016/9/29.
 */

public abstract class BasePresenter<V extends BaseDisplay> {
    private V mDisplay;

    @CallSuper
    public void attachUi(V mDisplay) {
        this.mDisplay = mDisplay;
    }

    @CallSuper
    public void detachUi() {
        mDisplay = null;
    }

    @CallSuper
    public void onDestroy() {
        Preconditions.checkState(!isUiAttached(), "View should been detached before destroy!");
    }

    protected boolean isUiAttached() {
        return mDisplay != null;
    }

    @NonNull
    protected V getDisplay() {
        Preconditions.checkState(mDisplay != null, "View has been detached!");
        return mDisplay;
    }

}
