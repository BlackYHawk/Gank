package com.hawk.gank.features.gank.home.fragments;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hawk.gank.R;
import com.hawk.gank.features.gank.home.GankPresenter;
import com.hawk.gank.model.bean.Gank;
import com.hawk.lib.base.ui.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by heyong on 2016/11/7.
 */

public class WelfareListFragment extends BaseGankListFragment<Gank> implements GankPresenter.GankListView {

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

        @BindView(R.id.iv_mm) SimpleDraweeView mmView;
        @BindView(R.id.tv_title) TextView titleView;

        public GankViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
            Gank mm = mDataList.get(position);

            String text = mm.description().length() > limit ? mm.description().substring(0, limit) +
                    "..." : mm.description();

            titleView.setText(text);
            mmView.setImageURI(Uri.parse(mm.url()));
        }

        @Override
        public void onItemClick(View view, int position) {
            if (hasCallbacks()) {
                Gank mm = mDataList.get(position);

                getCallbacks().showGankWealfare(mm.url());
            }
        }
    }
}
