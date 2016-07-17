package com.hawk.gank.ui.activity.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.hawk.gank.R;
import com.hawk.gank.data.entity.Gank;
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
 * Created by lan on 2016/6/29.
 */
public class MainActivity extends BaseActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private static final int PRELOAD_SIZE = 6;
    private boolean mIsFirstTimeTouchBottom = true;

    private int mPage = 1;
    private ArrayList<Gank> mGankList;
    private boolean mIsRequestDataRefresh = false;

    @BindView(R.id.drawLayout) DrawerLayout mDrawer;
    @BindView(R.id.navigationView) NavigationView mNavigationView;
    @BindView(R.id.swipeRefreshlayout) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    private ActionBarDrawerToggle drawerToggle;
    private MMAdapter mMMAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.e(TAG, "onCreate");
        setContentView(R.layout.ac_ui_main);

        initData();
        initView();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new Handler().postDelayed(() -> setRefresh(true), 358);
        loadData(true);
    }

    private void initData() {
        mGankList = new ArrayList<>();
        mMMAdapter = new MMAdapter(this, mGankList);
    }

    private void initView() {
        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

        };
        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);

        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark));
        mSwipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration((int)mStringFetcher.getDimensionRes(R.dimen.card_margin)));
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

    NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            item.setChecked(true);
            mDrawer.closeDrawer(GravityCompat.START);

            return true;
        }
    };

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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        logger.e(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        logger.e(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        logger.e(TAG, "onDestroy");
        super.onDestroy();
    }

}
