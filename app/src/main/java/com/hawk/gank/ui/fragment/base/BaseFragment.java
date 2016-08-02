package com.hawk.gank.ui.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hawk.gank.AppContext;
import com.hawk.gank.interfaces.Logger;
import com.hawk.gank.interfaces.StringFetcher;
import com.hawk.gank.ui.activity.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lan on 2016/6/29.
 */
public abstract class BaseFragment extends Fragment {
    protected int titleId = -1;
    private ViewGroup rootView;// 根视图
    private Unbinder unbinder;
    protected @Inject Logger logger;
    protected @Inject StringFetcher mStringFetcher;
    protected @Inject CompositeSubscription mSubscription;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof BaseActivity)
            ((BaseActivity) context).addFragment(toString(), this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() instanceof BaseActivity) {

            if(titleId != -1) {
                ((BaseActivity) getActivity()).setDisplayTitle(titleId);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (inflateContentView() > 0) {
            rootView = (ViewGroup) inflater.inflate(inflateContentView(), null);
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
    public void onDetach() {
        if (getActivity() != null && getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).removeFragment(this.toString());

        super.onDetach();
    }

    @Override
    public void onDestroy() {
        if (this.mSubscription != null) {
            this.mSubscription.unsubscribe();
        }
        super.onDestroy();
    }

    protected void setTitle(int strId) {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).setDisplayTitle(strId);
        }
    }

    protected AppContext getAppContext() {
        return ((BaseActivity) getActivity()).getAppContext();
    }

    protected void addSubscription(Subscription subscription) {
        mSubscription.add(subscription);
    }

    protected void dismiss() {
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            ((BaseActivity)getActivity()).backFragment();
        }
    }

    protected void finish() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    /**
     * 返回按键被点击了
     *
     * @return
     */
    public boolean onBackClick() {
        return false;
    }
}
