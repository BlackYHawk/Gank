package com.hawk.gank.features.gank.home.fragments;

import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hawk.gank.R;
import com.hawk.gank.features.gank.home.GankPresenter;
import com.hawk.gank.features.gank.home.GankUiCallbacks;
import com.hawk.lib.base.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by lan on 2016/7/30.
 */
public class SideMenuFragment extends BaseFragment<GankPresenter.GankSideMenuView, GankUiCallbacks,
        GankPresenter<GankPresenter.GankSideMenuView>> {

    @BindView(R.id.layHead) RelativeLayout layHead;
    @BindView(R.id.ivHead) SimpleDraweeView ivHead;
    @BindView(R.id.layCollect) RelativeLayout layCollect;

    @Override
    protected void onBindData() {

    }

    @OnClick(R.id.layCollect)
    void onLayoutClick(View view) {
        switch (view.getId()) {
            case R.id.layCollect :
                getCallbacks().showGankCollect();
                break;
        }
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
