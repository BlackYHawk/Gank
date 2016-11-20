package com.hawk.lib.base.ui.fragment;

import android.os.Parcelable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hawk.lib.base.R;
import com.hawk.lib.base.ui.adapter.BaseRecyclerAdapter;
import com.hawk.lib.base.ui.adapter.BaseViewHolder;
import com.hawk.lib.base.ui.widget.PullRecycler;
import com.hawk.lib.base.ui.widget.layoutmanager.ILayoutManager;
import com.hawk.lib.base.ui.widget.layoutmanager.MyLinearLayoutManager;
import com.hawk.lib.base.util.ObjectUtil;
import com.hawk.lib.mvp.ui.presenter.BasePresenter;
import com.hawk.lib.mvp.ui.view.BaseView;

import java.util.List;

/**
 * Created by heyong on 2016/11/5.
 */

public abstract class BaseListFragment<T extends Parcelable, V extends BaseView<VC>, VC, P extends BasePresenter<V, VC>>
        extends BaseFragment<V, VC, P> implements PullRecycler.OnRecyclerRefreshListener {

    protected PullRecycler recycler;
    protected BaseRecyclerAdapter mAdapter;
    protected List<T> mDataList;

    @Override
    protected void bindView(View rootView) {
        super.bindView(rootView);
        recycler = (PullRecycler) rootView.findViewById(R.id.mPullRecycler);
        recycler.setOnRefreshListener(this);
        recycler.setLayoutManager(getLayoutManager());
        recycler.addItemDecoration(getItemDecoration());
        recycler.enableLoadMore(true);
    }

    @Override
    protected void onBindData() {
        mAdapter = new RecyclerAdapter();
        recycler.setAdapter(mAdapter);
    }

    protected void setDataItems(List<T> items) {
        if (!ObjectUtil.isEmpty(items)) {
            mDataList = items;
            mAdapter.notifyDataSetChanged();
        }
        recycler.onRefreshCompleted();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_ui_base_list;
    }

    @Override
    protected boolean autoBindViews() {
        return false;
    }

    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getContext());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
    }

    protected boolean isSectionHeader(int position) {
        return false;
    }

    protected int getItemType(int position) {
        return 0;
    }

    protected abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);

    public class RecyclerAdapter extends BaseRecyclerAdapter {

        @Override
        protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        }

        @Override
        protected int getDataCount() {
            return mDataList != null ? mDataList.size() : 0;
        }

        @Override
        protected int getDataViewType(int position) {
            return getItemType(position);
        }

        @Override
        public boolean isSectionHeader(int position) {
            return BaseListFragment.this.isSectionHeader(position);
        }
    }

}
