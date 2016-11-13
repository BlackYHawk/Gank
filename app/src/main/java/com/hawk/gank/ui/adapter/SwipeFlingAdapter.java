package com.hawk.gank.ui.adapter;

import android.content.Context;

import com.hawk.gank.model.gank.Gank;
import com.hawk.gank.ui.item.PicItemView;

import java.util.ArrayList;

/**
 * Created by heyong on 16/7/17.
 */
public class SwipeFlingAdapter extends ABaseAdapter {

    public SwipeFlingAdapter(Context context, ArrayList<Gank> gankList) {
        super(context, gankList);
    }

    @Override
    protected AbstractItemView newItemView() {
        return new PicItemView(context);
    }
}
