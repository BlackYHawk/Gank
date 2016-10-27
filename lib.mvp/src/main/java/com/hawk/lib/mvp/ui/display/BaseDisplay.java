package com.hawk.lib.mvp.ui.display;

import android.support.v7.widget.Toolbar;

/**
 * Created by heyong on 2016/9/30.
 */

public interface BaseDisplay {

    void setActionBarTitle(CharSequence title);

    void setSupportActionBar(Toolbar toolbar, boolean handleBackground);

}
