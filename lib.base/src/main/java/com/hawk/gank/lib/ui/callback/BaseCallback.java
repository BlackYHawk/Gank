package com.hawk.gank.lib.ui.callback;

/**
 * Created by heyong on 2016/10/1.
 */

public interface BaseCallback<T> {

    void setCallback(T callback);

    boolean isModal();

}
