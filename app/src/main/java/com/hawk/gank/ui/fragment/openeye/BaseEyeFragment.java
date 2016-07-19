package com.hawk.gank.ui.fragment.openeye;

import android.os.Bundle;

import com.hawk.gank.http.OpenEyeIO;
import com.hawk.gank.interfaces.Logger;
import com.hawk.gank.interfaces.StringFetcher;
import com.hawk.gank.ui.activity.base.BaseActivity;
import com.hawk.gank.ui.fragment.base.BaseFragment;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by heyong on 16/7/18.
 */
public abstract class BaseEyeFragment extends BaseFragment {
    protected @Inject Logger logger;
    protected @Inject StringFetcher mStringFetcher;
    protected @Inject OpenEyeIO openEyeIO;
    protected @Inject CompositeSubscription mSubscription;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).component().inject(this);
        }
    }

    protected void addSubscription(Subscription subscription) {
        mSubscription.add(subscription);
    }
}