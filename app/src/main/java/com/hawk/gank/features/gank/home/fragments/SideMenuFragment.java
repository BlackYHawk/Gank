package com.hawk.gank.features.gank.home.fragments;

import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hawk.gank.R;
import com.hawk.gank.features.gank.home.GankPresenter;
import com.hawk.gank.features.gank.home.GankUiCallbacks;
import com.hawk.lib.base.ui.fragment.BaseFragment;

import butterknife.BindView;


/**
 * Created by lan on 2016/7/30.
 */
public class SideMenuFragment extends BaseFragment<GankPresenter.GankSideMenuView, GankUiCallbacks,
        GankPresenter<GankPresenter.GankSideMenuView>> {

    @BindView(R.id.layHead) RelativeLayout layHead;
    @BindView(R.id.ivHead) SimpleDraweeView ivHead;

    @Override
    protected void onBindData() {

    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_ui_sidebar;
    }

}
