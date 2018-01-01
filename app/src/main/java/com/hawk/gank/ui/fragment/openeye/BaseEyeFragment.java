package com.hawk.gank.ui.fragment.openeye;

import android.os.Bundle;

import com.hawk.gank.model.http.OpenEyeIO;
import com.hawk.gank.ui.fragment.base.BaseFragment;

import javax.inject.Inject;

/**
 * Created by heyong on 16/7/18.
 */
public abstract class BaseEyeFragment extends BaseFragment {
    protected @Inject
    OpenEyeIO openEyeIO;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//
//        if (getActivity() instanceof BaseActivity) {
//            ((BaseActivity) getActivity()).component().inject(this);
//        }
    }

}
