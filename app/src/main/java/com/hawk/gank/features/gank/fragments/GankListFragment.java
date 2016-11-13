package com.hawk.gank.features.gank.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hawk.gank.R;
import com.hawk.gank.features.gank.GankPresenter;
import com.hawk.gank.features.gank.GankUiCallbacks;
import com.hawk.gank.model.gank.Gank;
import com.hawk.gank.ui.widget.RatioImageView;
import com.hawk.lib.base.model.type.ListItem;
import com.hawk.lib.base.ui.adapter.BaseViewHolder;
import com.hawk.lib.base.ui.fragment.BaseListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heyong on 2016/11/7.
 */

public class GankListFragment extends BaseListFragment<Gank, GankPresenter.GankListView, GankUiCallbacks,
        GankPresenter<GankPresenter.GankListView>> implements GankPresenter.GankListView {

    public static GankListFragment newInstance(int type) {
        GankListFragment fragment = new GankListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);

        return fragment;
    }

    private int type = 0;
    private int limit = 48;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        type = getArguments().getInt("type", 0);
    }

    @Override
    public GankPresenter.GankQueryType getGankQueryType() {
        return GankPresenter.GankQueryType.LIST;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void setItems(List<ListItem<Gank>> listItems) {

    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mm, parent, false);
        return new GankViewHolder(view);
    }

    @Override
    public void onRefresh(int action) {

    }

    class GankViewHolder extends BaseViewHolder {

        @BindView(R.id.iv_mm)
        RatioImageView mmView;
        @BindView(R.id.tv_title)
        TextView titleView;

        public GankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mmView.setOriginalSize(50, 50);
        }

        @Override
        public void onBindViewHolder(int position) {
            Gank mm = mDataList.get(position);

            String text = mm.description().length() > limit ? mm.description().substring(0, limit) +
                    "..." : mm.description();

            titleView.setText(text);

            Glide.with(mmView.getContext())
                    .load(mm.url())
                    .centerCrop()
                    .into(mmView);
        }

    }
}
