package com.hawk.gank.ui.activity.pic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.hawk.gank.R;
import com.hawk.gank.data.entity.Gank;
import com.hawk.gank.ui.activity.base.BaseActivity;
import com.hawk.gank.ui.adapter.SwipeFlingAdapter;
import com.hawk.gank.ui.widget.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by heyong on 16/7/17.
 */
public class PicActivity extends BaseActivity {
    private final String TAG = PicActivity.class.getSimpleName();
    @BindView(R.id.swipeFlingView) SwipeFlingAdapterView mSwipeFlingView;

    private ArrayList<Gank> mGankList;
    private int mCurrent;
    private SwipeFlingAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.e(TAG, "onCreate");
        setContentView(R.layout.ac_ui_pic);
        mGankList = getIntent().getParcelableArrayListExtra("gankData");
        mCurrent = (Integer) getIntent().getIntExtra("current", 0);

        setDisplayBack();
        initData();
        initView();
    }

    private void initData() {
        mAdapter = new SwipeFlingAdapter(this, mGankList);
        mAdapter.setSelected(mCurrent);
    }

    private void initView() {
        mSwipeFlingView.setAdapter(mAdapter);
        mSwipeFlingView.setFlingListener(onFlingListener);
    }

    private SwipeFlingAdapterView.onFlingListener onFlingListener = new SwipeFlingAdapterView.onFlingListener() {
        @Override
        public void removeFirstObjectInAdapter() {
            mAdapter.removeItemAndRefresh(0);
        }

        @Override
        public void onLeftCardExit(Object dataObject) {

        }

        @Override
        public void onRightCardExit(Object dataObject) {

        }

        @Override
        public void onAdapterAboutToEmpty(int itemsInAdapter) {
            finish();
        }

        @Override
        public void onScroll(float progress, float scrollXProgress) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
