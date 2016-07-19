package com.hawk.gank.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hawk.gank.R;
import com.hawk.gank.data.entity.ItemBean;
import com.hawk.gank.ui.activity.video.VideoShowActivity;
import com.hawk.gank.ui.widget.RatioImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by heyong on 16/7/10.
 */
public class EyeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<ItemBean> mItemList;
    private int[] typeArray = {1, 2};

    public EyeAdapter(Context context, ArrayList<ItemBean> itemList) {
        mContext = context;
        mItemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemBean item = mItemList.get(position);

        if(viewHolder instanceof EyeViewHolder) {
            EyeViewHolder holder = (EyeViewHolder) viewHolder;

            String text = item.getData().getTitle();
            holder.tvTitle.setText(text);

            Picasso.with(mContext)
                    .load(item.getData().getCover().getDetail())
                    .resize(500, 500)
                    .centerCrop()
                    .into(holder.ivCover);
        }
        else if(viewHolder instanceof CategoryViewHolder) {
            CategoryViewHolder holder = (CategoryViewHolder) viewHolder;

            String text = item.getData().getText();
            holder.tvCategory.setText(text);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        String type = mItemList.get(position).getType();

        if(type.equals("video")) {
            return typeArray[0];
        }
        else {
            return typeArray[1];
        }
    }

    class EyeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layPlay) RelativeLayout layPlay;
        @BindView(R.id.ivCover) RatioImageView ivCover;
        @BindView(R.id.tvTitle) TextView tvTitle;

        public EyeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivCover.setOriginalSize(50, 50);
        }

        @OnClick(R.id.layPlay)
        public void click(View view) {
            Intent intent = new Intent(mContext, VideoShowActivity.class);
            intent.putExtra("eyeBean", mItemList.get(getAdapterPosition()));
            mContext.startActivity(intent);
        }

    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvCategory) TextView tvCategory;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}
