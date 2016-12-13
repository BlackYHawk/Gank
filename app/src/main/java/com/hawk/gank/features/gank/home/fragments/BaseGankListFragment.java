package com.hawk.gank.features.gank.home.fragments;

import com.hawk.gank.features.gank.home.GankPresenter;
import com.hawk.gank.features.gank.home.GankUiCallbacks;
import com.hawk.gank.model.error.RxError;
import com.hawk.lib.base.util.UIHelper;
import com.hawk.lib.base.ui.fragment.BaseListFragment;
import com.hawk.lib.base.ui.widget.PullRecycler;

import java.util.List;

/**
 * Created by heyong on 2016/11/20.
 */

public abstract class BaseGankListFragment<T> extends BaseListFragment<T, GankPresenter.BaseGankListView<T>, GankUiCallbacks,
        GankPresenter<GankPresenter.BaseGankListView<T>>> implements GankPresenter.BaseGankListView<T> {

    @Override
    public void showError(RxError error) {
        UIHelper.showToast(getActivity(), error.message());
        recycler.onRefreshCompleted();
    }

    @Override
    public void setItems(List<T> listItems) {
        setDataItems(listItems);
    }

    @Override
    public void scrollToTop() {
        if(isVisible() && getUserVisibleHint()) {
            recycler.setSelection(0);
        }
    }

    @Override
    public void onRefresh(int action) {
        if (action == PullRecycler.ACTION_PULL_TO_REFRESH) {
            getCallbacks().onPulledToTop();
        }
        else {
            getCallbacks().onScrolledToBottom();
        }
    }
}
