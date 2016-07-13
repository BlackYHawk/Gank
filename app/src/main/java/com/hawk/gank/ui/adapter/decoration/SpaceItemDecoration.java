package com.hawk.gank.ui.adapter.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by heyong on 16/7/12.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;

        int position = parent.getChildAdapterPosition(view);

        if(position == 0 || position == 1) {
            outRect.top = space;
        }

        if(position % 2 == 0) {
            outRect.left = space;
            outRect.right = space;
        }
        else {
            outRect.right = space;
        }
    }

}
