package com.hawk.gank.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hawk.gank.R;
import com.hawk.gank.data.entity.ItemBean;
import com.hawk.gank.ui.widget.RatioImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by heyong on 16/7/10.
 */
public class EyeAdapter extends RecyclerView.Adapter<EyeAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ItemBean> mItemList;
    private int limit = 48;

    public EyeAdapter(Context context, ArrayList<ItemBean> itemList) {
        mContext = context;
        mItemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_eye, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ItemBean item = mItemList.get(position);

        String text = item.getData().getTitle();
        viewHolder.tvTitle.setText(text);

        Picasso.with(mContext)
                .load(item.getData().getCover().getDetail())
                .resize(400, 400)
                .centerCrop()
                .into(viewHolder.ivCover);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivCover) RatioImageView ivCover;
        @BindView(R.id.tvTitle) TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivCover.setOriginalSize(50, 50);
        }

        @OnClick(R.id.ivCover)
        public void click(View view) {

        }

    }
}
