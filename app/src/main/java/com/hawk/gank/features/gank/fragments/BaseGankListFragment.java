package com.hawk.gank.features.gank.fragments;

import com.hawk.gank.features.gank.GankPresenter;
import com.hawk.gank.features.gank.GankUiCallbacks;
import com.hawk.gank.model.gank.Gank;
import com.hawk.lib.base.ui.fragment.BaseListFragment;
import com.hawk.lib.base.ui.widget.PullRecycler;

import java.util.List;

/**
 * Created by heyong on 2016/11/20.
 */

public abstract class BaseGankListFragment extends BaseListFragment<Gank, GankPresenter.GankListView, GankUiCallbacks,
        GankPresenter<GankPresenter.GankListView>>  implements GankPresenter.GankListView {

    @Override
    public void setItems(List<Gank> listItems) {
        setDataItems(listItems);
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
