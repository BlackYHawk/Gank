package com.hawk.gank.features.gank.home.openeye;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.hawk.gank.R;
import com.hawk.gank.features.gank.home.GankPresenter;
import com.hawk.gank.features.gank.home.gank.BaseGankListFragment;
import com.hawk.gank.model.bean.entity.ItemBean;
import com.hawk.gank.ui.adapter.decoration.EyeSpaceItemDecoration;
import com.hawk.gank.util.StringUtil;
import com.hawk.lib.base.ui.adapter.BaseViewHolder;

import butterknife.BindView;


/**
 * Created by heyong on 2018/1/1.
 */

public class EyeListFragment extends BaseGankListFragment<ItemBean> implements GankPresenter.OpenEyeListView {
    private int[] typeArray = {1, 2};

    public static EyeListFragment newInstance() {
        EyeListFragment fragment = new EyeListFragment();

        return fragment;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public GankPresenter.GankQueryType getGankQueryType() {
        return GankPresenter.GankQueryType.OPENEYE;
    }

    @Override
    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new EyeSpaceItemDecoration((int) getResources().getDimension(R.dimen.card_margin));
    }

    @Override
    protected int getItemType(int position) {
        String type = mDataList.get(position).getType();

        if(type.equals("video")) {
            return typeArray[0];
        }
        else {
            return typeArray[1];
        }
    }

    @Override
    protected BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        if(viewType == typeArray[0]) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_eye, parent, false);
            return new EyeViewHolder(v);
        }
        else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_eye_category, parent, false);
            return new CategoryViewHolder(v);
        }
    }

    class EyeViewHolder extends BaseViewHolder {
        @BindView(R.id.ivCover) SimpleDraweeView ivCover;
        @BindView(R.id.tvTitle) TextView tvTitle;

        public EyeViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
            ItemBean item = mDataList.get(position);
            String text = item.getData().getTitle();
            String imgUrl = "";

            if(!StringUtil.isEmpty(item.getData().getCover().getDetail())) {
                imgUrl = item.getData().getCover().getDetail();
            }

            tvTitle.setText(text);

            if(!StringUtil.isEmpty(imgUrl)) {
                ivCover.setImageURI(Uri.parse(imgUrl));
            }
            else {
                ivCover.setImageURI(Uri.EMPTY);
            }
        }

        @Override
        public void onItemClick(View view, int position) {
            if (hasCallbacks()) {
                getCallbacks().showOpenEye(mDataList.get(position));
            }
        }
    }

    class CategoryViewHolder extends BaseViewHolder {
        @BindView(R.id.tvCategory) TextView tvCategory;

        public CategoryViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
            ItemBean item = mDataList.get(position);

            String text = item.getData().getText();
            tvCategory.setText(text);
        }
    }

}
