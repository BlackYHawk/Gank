package com.hawk.gank.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hawk.gank.R;
import com.hawk.gank.data.entity.Gank;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heyong on 16/7/10.
 */
public class MMAdapter extends RecyclerView.Adapter<MMAdapter.ViewHolder> {

    private Context mContext;
    private List<Gank> mGankList;

    public MMAdapter(Context context, List<Gank> meizhiList) {
        mContext = context;
        mGankList = meizhiList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meizhi, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Gank mm = mGankList.get(position);
        int limit = 48;
        String text = mm.getDesc().length() > limit ? mm.getDesc().substring(0, limit) +
                "..." : mm.getDesc();

        viewHolder.titleView.setText(text);

        Picasso.with(mContext)
                .load(mm.getUrl())
                .into(viewHolder.meizhiView);
    }

    @Override
    public int getItemCount() {
        return mGankList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_meizhi) ImageView meizhiView;
        @BindView(R.id.tv_title) TextView titleView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
         //   meizhiView.setOriginalSize(50, 50);
        }

    }
}