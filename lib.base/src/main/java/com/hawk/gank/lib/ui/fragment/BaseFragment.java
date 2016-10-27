package com.hawk.gank.lib.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hawk.gank.lib.BaseApplication;
import com.hawk.gank.lib.interfaces.Logger;
import com.hawk.gank.lib.interfaces.StringFetcher;
import com.hawk.gank.lib.ui.activity.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lan on 2016/6/29.
 */
public abstract class BaseFragment extends Fragment {
    private View rootView;// 根视图
    private Unbinder unbinder;
    protected @Inject Logger logger;
    protected @Inject StringFetcher mStringFetcher;
    protected @Inject CompositeSubscription mSubscription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflateContentView() > 0) {
            rootView = inflater.inflate(inflateContentView(), container, false);
            rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            unbinder = ButterKnife.bind(this, rootView);

            layoutInit(inflater, savedInstanceState);

            return rootView;
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    abstract protected int inflateContentView();

    /**
     * 子类重写这个方法，初始化视图
     *
     * @param inflater
     * @param savedInstanceSate
     */
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {

    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        if (this.mSubscription != null) {
            this.mSubscription.unsubscribe();
        }
        super.onDestroy();
    }

    protected BaseApplication getApplicationContext() {
        return ((BaseActivity) getActivity()).getApplicationContext();
    }

    protected void addSubscription(Subscription subscription) {
        mSubscription.add(subscription);
    }

}
