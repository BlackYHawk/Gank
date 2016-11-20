package com.hawk.gank.features.gank.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hawk.gank.R;
import com.hawk.gank.features.gank.GankPresenter;
import com.hawk.gank.model.gank.Gank;
import com.hawk.gank.ui.widget.RatioImageView;
import com.hawk.lib.base.ui.adapter.BaseViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heyong on 2016/11/7.
 */

public class WelfareListFragment extends BaseGankListFragment {

    public static WelfareListFragment newInstance() {
        WelfareListFragment fragment = new WelfareListFragment();

        return fragment;
    }

    private int limit = 48;

    @Override
    public GankPresenter.GankQueryType getGankQueryType() {
        return GankPresenter.GankQueryType.WELFARE;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mm, parent, false);
        return new GankViewHolder(view);
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
