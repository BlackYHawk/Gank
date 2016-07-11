package com.hawk.gank.modules;


import android.support.v4.app.Fragment;

import com.hawk.gank.qualifiers.ActivityScope;
import com.hawk.gank.ui.activity.base.BaseActivity;

import dagger.Subcomponent;

/**
 * Created by lan on 2016/6/29.
 */
@ActivityScope
@Subcomponent(modules = ActModule.class)
public interface ActComponent {

    void inject(BaseActivity baseActivity);

    void inject(Fragment baseFragment);

    BaseActivity activity();

}
