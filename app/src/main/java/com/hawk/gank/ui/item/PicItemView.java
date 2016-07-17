package com.hawk.gank.ui.item;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hawk.gank.R;
import com.hawk.gank.data.entity.Gank;
import com.hawk.gank.ui.adapter.ABaseAdapter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

/**
 * Created by heyong on 16/7/17.
 */
public class PicItemView extends ABaseAdapter.AbstractItemView<Gank> {
    private Context mContext;
    private int width, height;
    private final int gap = 30;
    @BindView(R.id.ivPic) ImageView ivPic;
    @BindView(R.id.tvTitle) TextView tvTitle;

    public PicItemView(Context context) {
        mContext = context;
        float density = mContext.getResources().getDisplayMetrics().density;
        width = (int)(mContext.getResources().getDisplayMetrics().widthPixels - gap*density);
        height = width;
    }

    @Override
    public int inflateViewId() {
        return R.layout.item_pic;
    }

    @Override
    public void bindingData(View convertView, Gank data) {
        Picasso.with(mContext)
                .load(data.url)
                .resize(width, height)
                .centerCrop()
                .into(ivPic);
        tvTitle.setText(data.desc);
    }

}
