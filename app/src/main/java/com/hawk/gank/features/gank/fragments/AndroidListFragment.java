package com.hawk.gank.features.gank.fragments;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hawk.gank.R;
import com.hawk.gank.features.gank.GankPresenter;
import com.hawk.gank.model.gank.Gank;
import com.hawk.gank.util.StringUtil;
import com.hawk.lib.base.ui.adapter.BaseViewHolder;
import com.hawk.lib.base.util.ObjectUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heyong on 2016/11/7.
 */

public class AndroidListFragment extends BaseGankListFragment {

    public static AndroidListFragment newInstance() {
        AndroidListFragment fragment = new AndroidListFragment();

        return fragment;
    }

    @Override
    public GankPresenter.GankQueryType getGankQueryType() {
        return GankPresenter.GankQueryType.ANDROID;
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

        @BindView(R.id.ivAverator) SimpleDraweeView ivAverator;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvTime) TextView tvTime;

        public GankViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
            Gank mm = mDataList.get(position);

            String publishTime = StringUtil.formatDisplayTime(mm.publishedAt());

            tvTitle.setText(mm.description());
            tvTime.setText(publishTime);
            if(!ObjectUtil.isEmpty(mm.images())) {
                ivAverator.setImageURI(Uri.parse(mm.images().get(0)));
            }
        }

    }
}
