package com.hawk.gank.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hawk.gank.R;
import com.hawk.gank.data.entity.Gank;
import com.hawk.gank.ui.activity.pic.PicActivity;
import com.hawk.gank.ui.widget.RatioImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by heyong on 16/7/10.
 */
public class MMAdapter extends RecyclerView.Adapter<MMAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Gank> mGankList;
    private int limit = 48;

    public MMAdapter(Context context, ArrayList<Gank> gankList) {
        mContext = context;
        mGankList = gankList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mm, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Gank mm = mGankList.get(position);

        String text = mm.desc.length() > limit ? mm.desc.substring(0, limit) +
                "..." : mm.desc;

        viewHolder.titleView.setText(text);

        Picasso.with(mContext)
                .load(mm.url)
                .resize(300, 300)
                .centerCrop()
                .into(viewHolder.mmView);
    }

    @Override
    public int getItemCount() {
        return mGankList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_mm) RatioImageView mmView;
        @BindView(R.id.tv_title) TextView titleView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mmView.setOriginalSize(50, 50);
        }

        @OnClick(R.id.iv_mm)
        public void click(View view) {
            ArrayList<Gank> gankList = new ArrayList<Gank>();
            gankList.addAll(mGankList.subList(getAdapterPosition(), mGankList.size()));

            Intent intent = new Intent(mContext, PicActivity.class);
            intent.putParcelableArrayListExtra("gankData", gankList);
            mContext.startActivity(intent);
        }

    }
}
