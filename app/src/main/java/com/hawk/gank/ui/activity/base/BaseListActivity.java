package com.hawk.gank.ui.activity.base;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AdapterView;

/**
 * Created by heyong on 16/7/12.
 */
public class BaseListActivity<E extends AbsListView> extends BaseActivity
        implements AbsListView.OnScrollListener {

    private boolean DEBUG = true;
    private int mFirstVisiblePosition;
    private int mFirstVisiblePositionTop;
    private boolean mLoadMoreIsAtBottom;
    private int mLoadMoreRequestedItemCount;

    private E mListView;

    protected void moveListViewToSavedPositions() {
        final E list = getListView();
        if (mFirstVisiblePosition != AdapterView.INVALID_POSITION
                && list.getFirstVisiblePosition() <= 0) {
            list.post(new Runnable() {
                @Override
                public void run() {
                    list.setSelection(mFirstVisiblePosition);
                }
            });
        }
    }

    private void saveListViewPosition() {
        E listView = getListView();

        mFirstVisiblePosition = listView.getFirstVisiblePosition();

        if (mFirstVisiblePosition != AdapterView.INVALID_POSITION && listView.getChildCount() > 0) {
            mFirstVisiblePositionTop = listView.getChildAt(0).getTop();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && mLoadMoreIsAtBottom) {
            if (onScrolledToBottom()) {
                mLoadMoreRequestedItemCount = absListView.getCount();
                mLoadMoreIsAtBottom = false;
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        mLoadMoreIsAtBottom = totalItemCount > mLoadMoreRequestedItemCount
                && firstVisibleItem + visibleItemCount == totalItemCount;
    }

    protected boolean onScrolledToBottom() {
        if (DEBUG) {
            Log.e("BaseSwipeRefresh", "onScrolledToBottom");
        }

        return false;
    }

    @Override
    protected void onPause() {
        saveListViewPosition();
        super.onPause();
    }

    protected E getListView() {
        return mListView;
    }

    protected void setListView(E listView) {
        mListView = listView;

        if(getListView() != null) {
            getListView().setOnScrollListener(this);
        }
    }
}
