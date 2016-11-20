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
import com.hawk.lib.base.util.ObjectUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heyong on 2016/11/7.
 */

public class IosListFragment extends BaseGankListFragment {

    public static IosListFragment newInstance() {
        IosListFragment fragment = new IosListFragment();

        return fragment;
    }

    private int limit = 48;

    @Override
    public GankPresenter.GankQueryType getGankQueryType() {
        return GankPresenter.GankQueryType.IOS;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_android, parent, false);
        return new GankViewHolder(view);
    }

    class GankViewHolder extends BaseViewHolder {

        @BindView(R.id.ivAverator)
        RatioImageView ivAverator;
        @BindView(R.id.tvTitle)
        TextView tvTitle;

        public GankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivAverator.setOriginalSize(50, 50);
        }

        @Override
        public void onBindViewHolder(int position) {
            Gank mm = mDataList.get(position);

            String text = mm.description().length() > limit ? mm.description().substring(0, limit) +
                    "..." : mm.description();

            tvTitle.setText(text);

            if(!ObjectUtil.isEmpty(mm.images())) {
                Glide.with(ivAverator.getContext())
                        .load(mm.images().get(0))
                        .centerCrop()
                        .into(ivAverator);
            }
        }

    }
}
