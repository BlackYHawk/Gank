package com.hawk.gank.modules;


import com.hawk.gank.qualifiers.ActivityScope;
import com.hawk.gank.ui.activity.base.BaseActivity;
import com.hawk.gank.ui.fragment.account.BaseAccountFragment;
import com.hawk.gank.ui.fragment.gank.BaseGankFragment;
import com.hawk.gank.ui.fragment.openeye.BaseEyeFragment;

import dagger.Subcomponent;

/**
 * Created by lan on 2016/6/29.
 */
@ActivityScope
@Subcomponent(modules = ActModule.class)
public interface ActComponent {

    void inject(BaseActivity baseActivity);

    void inject(BaseAccountFragment baseFragment);

    void inject(BaseGankFragment baseFragment);

    void inject(BaseEyeFragment baseFragment);

    BaseActivity activity();

}
