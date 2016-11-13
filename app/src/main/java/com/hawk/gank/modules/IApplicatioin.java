package com.hawk.gank.modules;

import android.support.annotation.NonNull;

/**
 * Created by heyong on 2016/11/4.
 */

public interface IApplicatioin {

    /**
     * get the {@link AppComponent} object.
     *
     * @return the {@link AppComponent} object.
     */
    @NonNull
    AppComponent appComponent();

}
