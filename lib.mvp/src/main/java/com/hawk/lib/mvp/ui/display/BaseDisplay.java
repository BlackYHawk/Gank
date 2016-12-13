package com.hawk.lib.mvp.ui.display;

/**
 * Created by heyong on 2016/11/7.
 */

public interface BaseDisplay {

    void finish();

    void showUpNavigation(boolean show);

    void setActionBarTitle(String title);

    void setSupportActionBar(Object toolbar);

}
