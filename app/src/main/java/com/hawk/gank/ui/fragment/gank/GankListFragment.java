package com.hawk.gank.ui.fragment.gank;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;

import com.hawk.gank.R;
import com.hawk.gank.data.entity.Gank;
import com.hawk.gank.ui.activity.base.BaseActivity;
import com.hawk.gank.ui.adapter.MMAdapter;
import com.hawk.gank.ui.adapter.decoration.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by heyong on 16/7/18.
 */
public class GankListFragment extends BaseGankFragment {
    private final String TAG = GankListFragment.class.getSimpleName();
    private BaseActivity activity;
    @BindView(R.id.swipeRefreshlayout) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    private static final int PRELOAD_SIZE = 6;
    private boolean mIsFirstTimeTouchBottom = true;

    private int mPage = 1;
    private ArrayList<Gank> mGankList;
    private boolean mIsRequestDataRefresh = false;
    private MMAdapter mMMAdapter;

    public static GankListFragment newInstance() {
        return new GankListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();

        initData();
    }

    private void initData() {
        mGankList = new ArrayList<>();
        mMMAdapter = new MMAdapter(activity, mGankList);
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(activity, R.color.colorPrimary),
                ContextCompat.getColor(activity, R.color.colorAccent),
                ContextCompat.getColor(activity, R.color.colorPrimaryDark));
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration((int)activity.getResources().getDimension(R.dimen.card_margin)));
        mRecyclerView.setAdapter(mMMAdapter);
        mRecyclerView.addOnScrollListener(onBottomListener(layoutManager));
    }

    private void loadData(boolean refresh) {
        Subscription s = gankIO.getMMData(mPage)
                .subscribeOn(Schedulers.io())
                .map(gankData -> gankData.results)
                .flatMap(Observable::from)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedDate.compareTo(gankData1.publishedDate))
                .doOnNext(this::saveLocalData)
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> setRefresh(false))
                .subscribe(gankList -> {
                    if (refresh) {
                        mGankList.clear();
                    }
                    mGankList.addAll(gankList);
                    mMMAdapter.notifyDataSetChanged();
                    setRefresh(false);
                }, throwable -> loadError(throwable));
        addSubscription(s);
    }

    private void saveLocalData(List<Gank> gankList) {

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
        mPage = 1;
    }

    private void loadMore() {
        mPage += 1;
    }

    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadRefresh();
            loadData(true);
        }
    };

    RecyclerView.OnScrollListener onBottomListener(StaggeredGridLayoutManager layoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override public void onScrolled(RecyclerView rv, int dx, int dy) {
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >=
                                mMMAdapter.getItemCount() - PRELOAD_SIZE;
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

    @Override
    public void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> setRefresh(true), 358);
        loadData(true);
    }
}
