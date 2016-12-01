package com.hawk.lib.mvp.ui.display;

import android.support.v4.widget.DrawerLayout;

/**
 * Created by heyong on 2016/11/7.
 */

public interface BaseDisplay {

    void finish();

    void showUpNavigation(boolean show);

    void setDrawerLayout(DrawerLayout drawerLayout);

    void setSupportActionBar(Object toolbar);

}
