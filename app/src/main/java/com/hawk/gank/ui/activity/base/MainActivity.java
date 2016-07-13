package com.hawk.gank.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.google.android.agera.BaseObservable;
import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;
import com.hawk.gank.R;
import com.hawk.gank.data.GankData;
import com.hawk.gank.data.entity.Gank;
import com.hawk.gank.ui.adapter.MMAdapter;
import com.hawk.gank.ui.adapter.decoration.SpaceItemDecoration;
import com.hawk.gank.util.UIHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;


/**
 * Created by lan on 2016/6/29.
 */
public class MainActivity extends BaseActivity implements Updatable {
    private final String TAG = MainActivity.class.getSimpleName();

    private static final int PRELOAD_SIZE = 6;
    private boolean mIsFirstTimeTouchBottom = true;

    private Executor networkExecutor;
    private OnRefreshObservable refreshObservable;
    private Repository<Result<GankData>> gankRepository;
    private int mPage = 0;
    private List<Gank> mGankList;
    private boolean refresh = true;     //是否刷新

    @BindView(R.id.swipeRefreshlayout) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    private MMAdapter mMMAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.e(TAG, "onCreate");
        setContentView(R.layout.ac_ui_main);

        initData();
        initView();
    }

    private void initData() {
        mGankList = new ArrayList<>();
        mMMAdapter = new MMAdapter(this, mGankList);
        refreshObservable = new OnRefreshObservable();
        networkExecutor = Executors.newSingleThreadExecutor();

        gankRepository = Repositories.repositoryWithInitialValue(Result.<GankData>absent())
                .observe(refreshObservable)
                .onUpdatesPerLoop()
                .goTo(networkExecutor)
                .thenGetFrom(gankIO.getMMData(mPage))
                .compile();
    }

    private void initView() {
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark));
        mSwipeRefreshLayout.setOnRefreshListener(refreshObservable);

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration((int)mStringFetcher.getDimensionRes(R.dimen.card_margin)));
        mRecyclerView.setAdapter(mMMAdapter);
        mRecyclerView.addOnScrollListener(onBottomListener(layoutManager));
    }

    @Override
    public void update() {
        //进行获取数据的判定
        //如果拿到的是最初的数据就开始刷新
        if (gankRepository.get().isAbsent()) {
            mSwipeRefreshLayout.setRefreshing(true);
            //如果数据获取失败就提示
        } else if (gankRepository.get().failed()) {
            UIHelper.showToast(this, R.string.error);
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            if(refresh) {
                mGankList.clear();
            }
            mGankList.addAll(gankRepository.get().get().getResults());
            mMMAdapter.notifyDataSetChanged();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

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
                        loadData();
                    } else {
                        mIsFirstTimeTouchBottom = false;
                    }
                }
            }
        };
    }

    private void loadData() {
    }

    private void loadRefresh() {
        refresh = true;
        mPage = 0;
    }

    private void loadMore() {
        refresh = false;
        mPage += 1;
    }

    @Override
    protected void onResume() {
        logger.e(TAG, "onResume");
        super.onResume();
        gankRepository.addUpdatable(this);
    }

    @Override
    protected void onPause() {
        logger.e(TAG, "onPause");
        gankRepository.removeUpdatable(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        logger.e(TAG, "onDestroy");
        super.onDestroy();
    }

    class OnRefreshObservable extends BaseObservable implements
            SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            loadRefresh();
            dispatchUpdate();
        }
    }
}
