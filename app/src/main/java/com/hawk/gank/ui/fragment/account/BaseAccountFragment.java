package com.hawk.gank.ui.fragment.account;

import android.os.Bundle;

import com.hawk.gank.http.LeanCloudIO;
import com.hawk.gank.ui.fragment.base.BaseFragment;

import javax.inject.Inject;

/**
 * Created by heyong on 16/7/23.
 */
public abstract class BaseAccountFragment extends BaseFragment {
    protected @Inject LeanCloudIO leanCloudIO;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


}
