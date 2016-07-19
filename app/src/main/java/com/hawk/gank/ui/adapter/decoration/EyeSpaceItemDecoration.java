package com.hawk.gank.ui.adapter.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by heyong on 16/7/12.
 */
public class EyeSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public EyeSpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;
    }

}
