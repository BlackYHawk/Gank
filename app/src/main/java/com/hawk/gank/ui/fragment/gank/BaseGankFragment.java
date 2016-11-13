package com.hawk.gank.ui.fragment.gank;

import android.os.Bundle;

import com.hawk.gank.model.gank.GankIO;
import com.hawk.gank.ui.fragment.base.BaseFragment;

import javax.inject.Inject;

/**
 * Created by heyong on 16/7/18.
 */
public abstract class BaseGankFragment extends BaseFragment {
    protected @Inject
    GankIO gankIO;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        if (getActivity() instanceof BaseActivity) {
//            ((BaseActivity) getActivity()).component().inject(this);
//        }
    }

}
