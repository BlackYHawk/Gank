package com.hawk.gank.ui.fragment.openeye;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.hawk.gank.R;
import com.hawk.gank.data.entity.ItemBean;
import com.hawk.gank.ui.activity.base.BaseActivity;
import com.hawk.gank.ui.adapter.EyeAdapter;
import com.hawk.gank.ui.adapter.decoration.EyeSpaceItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by heyong on 16/7/19.
 */
public class EyeListFragment extends BaseEyeFragment {
    private final String TAG = EyeListFragment.class.getSimpleName();
    private BaseActivity activity;
    @BindView(R.id.swipeRefreshlayout) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    private int mPage = 0;
    private String date = "";
    private static final int PRELOAD_SIZE = 1;
    private boolean mIsFirstTimeTouchBottom = true;
    private boolean mIsRequestDataRefresh = false;
    private ArrayList<ItemBean> mItemList;
    private EyeAdapter mEyeAdapter;

    public static EyeListFragment newInstance() {
        return new EyeListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();

        initData();
    }

    private void initData() {
        mItemList = new ArrayList<>();
        mEyeAdapter = new EyeAdapter(activity, mItemList);

        date = String.valueOf(System.currentTimeMillis());
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(activity, R.color.colorPrimary),
                ContextCompat.getColor(activity, R.color.colorAccent),
                ContextCompat.getColor(activity, R.color.colorPrimaryDark));
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new EyeSpaceItemDecoration((int)activity.getResources().getDimension(R.dimen.card_margin)));
        mRecyclerView.setAdapter(mEyeAdapter);
        mRecyclerView.addOnScrollListener(onBottomListener(layoutManager));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logger.e(TAG, "onActivityCreated");
        new Handler().postDelayed(() -> setRefresh(true), 358);
        loadRefresh();
        loadData(true);
    }

    private void loadData(boolean refresh) {
        Subscription s = openEyeIO.getDailyData(date)
                .subscribeOn(Schedulers.io())
                .map(eyeData -> eyeData.getIssueList().get(0).getItemList())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> setRefresh(false))
                .subscribe(itemList -> {
                    if (refresh) {
                        mItemList.clear();
                    }
                    mItemList.addAll(itemList);
                    mEyeAdapter.notifyDataSetChanged();
                    setRefresh(false);
                }, throwable -> loadError(throwable));
        addSubscription(s);
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
    }

    public void setRefresh(boolean requestDataRefresh) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        if (!requestDataRefresh) {
            mIsRequestDataRefresh = false;
            // 防止刷新消失太快，让子弹飞一会儿.
            mSwipeRefreshLayout.postDelayed(new Runnable() {
                @Override public void run() {
                    if (mSwipeRefreshLayout != null) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }, 1000);
        } else {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    private void loadRefresh() {
        mPage = 0;
        date = String.valueOf(Calendar.getInstance().getTimeInMillis());
    }

    private void loadMore() {
        mPage++;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -mPage);
        date = String.valueOf(calendar.getTimeInMillis());
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadRefresh();
            loadData(true);
        }
    };

    RecyclerView.OnScrollListener onBottomListener(LinearLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(RecyclerView rv, int dx, int dy) {
                boolean isBottom = layoutManager.findLastVisibleItemPosition() >=
                                mEyeAdapter.getItemCount() - PRELOAD_SIZE;
                if (!mSwipeRefreshLayout.isRefreshing() && isBottom) {
                    if (!mIsFirstTimeTouchBottom) {
                        mSwipeRefreshLayout.setRefreshing(true);
                        loadMore();
                        loadData(false);
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }

    @Override
    protected int inflateContentView() {
        return R.layout.ac_ui_gank_list;
    }

}
