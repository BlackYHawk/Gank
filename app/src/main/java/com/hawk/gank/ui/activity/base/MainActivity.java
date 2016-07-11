package com.hawk.gank.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.google.android.agera.Repositories;
import com.google.android.agera.Repository;
import com.google.android.agera.Result;
import com.google.android.agera.Updatable;
import com.hawk.gank.R;
import com.hawk.gank.data.GankData;
import com.hawk.gank.interfaces.OnRefreshObservable;
import com.hawk.gank.ui.adapter.MMAdapter;
import com.hawk.gank.util.UIHelper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;


/**
 * Created by lan on 2016/6/29.
 */
public class MainActivity extends BaseActivity implements Updatable {
    private final String TAG = MainActivity.class.getSimpleName();

    private OnRefreshObservable refreshObservable;
    private Repository<Result<GankData>> gankRepository;
    private int page = 0;

    @BindView(R.id.swipeRefreshlayout) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    private MMAdapter mMMAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.e(TAG, "onCreate");
        setContentView(R.layout.ac_ui_main);

        initView();
        initData();
    }

    private void initData() {
        Executor networkExecutor = Executors.newSingleThreadExecutor();

        gankRepository = Repositories.repositoryWithInitialValue(Result.<GankData>absent())
                .observe(refreshObservable)
                .onUpdatesPerLoop()
                .goTo(networkExecutor)
                .thenGetFrom(gankIO.getMMData(page))
                .compile();
    }

    private void initView() {
        refreshObservable = new OnRefreshObservable();
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark));
        mSwipeRefreshLayout.setOnRefreshListener(refreshObservable);

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
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
            mMMAdapter = new MMAdapter(this, gankRepository.get().get().getResults());
            mRecyclerView.setAdapter(mMMAdapter);
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onResume() {
        logger.e(TAG, "onResume");
        super.onResume();
        gankRepository.addUpdatable(this);

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                update();
            }
        });
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
}
