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
import com.hawk.lib.base.imageloader.FrescoUtil;
import com.hawk.lib.base.ui.adapter.BaseViewHolder;
import com.hawk.lib.base.util.ObjectUtil;

import butterknife.BindView;
import io.vov.vitamio.utils.Log;

/**
 * Created by heyong on 2016/11/7.
 */

public class AndroidListFragment extends BaseGankListFragment<Gank> implements GankPresenter.GankListView {

    public static AndroidListFragment newInstance() {
        AndroidListFragment fragment = new AndroidListFragment();

        return fragment;
    }

    private int limit = 48;

    @Override
    public GankPresenter.GankQueryType getGankQueryType() {
        return GankPresenter.GankQueryType.ANDROID;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    protected int getItemType(int position) {
        return super.getItemType(position);
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
        }

        @Override
        public void onBindViewHolder(int position) {
            Gank mm = mDataList.get(position);

            String publishTime = StringUtil.formatDisplayTime(mm.publishedAt());
            String description = mm.description().length() > limit ? mm.description().substring(0, limit) +
                    "..." : mm.description();
            String imgUrl = "";

            if(!ObjectUtil.isEmpty(mm.images())) {
                imgUrl = mm.images().get(0);
            }

            tvTitle.setText(description);
            tvTime.setText(publishTime);

            if(!StringUtil.isEmpty(imgUrl)) {
                FrescoUtil.load(ivAverator, Uri.parse(imgUrl));
            }
            else {
                Log.e("test", "1");
                ivAverator.setImageURI(Uri.EMPTY);
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
