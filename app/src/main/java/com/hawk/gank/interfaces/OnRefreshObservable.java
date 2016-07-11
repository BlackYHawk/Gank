package com.hawk.gank.interfaces;

import android.support.v4.widget.SwipeRefreshLayout;

import com.google.android.agera.BaseObservable;

/**
 * Created by heyong on 16/7/11.
 */
public class OnRefreshObservable extends BaseObservable implements
        SwipeRefreshLayout.OnRefreshListener {

    @Override
    public void onRefresh() {
        dispatchUpdate();
    }
}
