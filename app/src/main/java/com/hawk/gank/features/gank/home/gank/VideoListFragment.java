package com.hawk.gank.features.gank.home.gank;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hawk.gank.R;
import com.hawk.gank.features.gank.home.GankPresenter;
import com.hawk.gank.model.bean.Gank;
import com.hawk.gank.util.StringUtil;
import com.hawk.lib.base.ui.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by heyong on 2016/11/7.
 */

public class VideoListFragment extends BaseGankListFragment<Gank> implements GankPresenter.GankListView {

    public static VideoListFragment newInstance() {
        VideoListFragment fragment = new VideoListFragment();

        return fragment;
    }

    private int limit = 48;

    @Override
    public GankPresenter.GankQueryType getGankQueryType() {
        return GankPresenter.GankQueryType.VIDEO;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
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
            String imgUrl = "";

            if(!StringUtil.isEmpty(mm.url())) {
                imgUrl = mm.url();
            }

            titleView.setText(text);

            if(!StringUtil.isEmpty(imgUrl)) {
                mmView.setImageURI(Uri.parse(imgUrl));
            }
            else {
                mmView.setImageURI(Uri.EMPTY);
            }
        }

        @Override
        public void onItemClick(View view, int position) {
            if (hasCallbacks()) {
                getCallbacks().showGankWeb(mDataList.get(position));
            }
        }
    }
}
