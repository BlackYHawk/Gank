package com.hawk.gank.features.gank.home;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.hawk.gank.AppContext;
import com.hawk.gank.R;
import com.hawk.gank.model.bean.Gank;
import com.hawk.gank.util.StringUtil;
import com.hawk.lib.base.ui.activity.BaseActivity;
import com.hawk.lib.base.ui.widget.ProgressWebView;
import com.hawk.lib.base.util.ObjectUtil;

import butterknife.BindView;

/**
 * Created by lan on 2016/8/10.
 */
public class GankWebActivity extends BaseActivity<GankView, GankUiCallbacks, GankPresenter<GankView>, GankComponent> {
    private static final String TAG = GankWebActivity.class.getSimpleName();
    @BindView(R.id.webview) ProgressWebView webview;
    private Gank gank;
    private boolean collect = false;   //是否已收藏

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDisplayBack();

        init();
        mPresenter.onGankCollectExist(gank);
    }

    private void init() {
        gank = getIntent().getParcelableExtra("gank");

        if(ObjectUtil.isEmpty(gank) || StringUtil.isEmpty(gank.url())) {
            finish();
        }
        webview.loadUrl(gank.url());
    }

    @Override
    protected void bindView() {
        super.bindView();
        webview.setOnInteractive(onWebInteractive);
    }

    public void menuValidate(boolean exist) {
        collect = exist;
        invalidateOptionsMenu();
    }

    private ProgressWebView.OnWebInteractive onWebInteractive = new ProgressWebView.OnWebInteractive() {
        @Override
        public void onReceiveTitle(String title) {
            setTitle(title);
        }
    };

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (collect) {
            MenuItem menuItem = menu.findItem(R.id.collect);
            menuItem.setTitle(getString(R.string.menu_collect_cancel));
        }
        else {
            MenuItem menuItem = menu.findItem(R.id.collect);
            menuItem.setTitle(getString(R.string.menu_collect));
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_detail_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(webview.canGoBack()) {
                    webview.goBack();
                }
                else {
                    finish();
                }
                break;
            case R.id.collect :
                mPresenter.onGankCollectClick(collect, gank);
                break;
            case R.id.close :
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if(webview != null) {   // 恢复网页中正在播放的视频
                webview.onResume();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if(webview != null) {   // 暂停网页中正在播放的视频
                webview.onPause();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(webview != null) {
            webview.destroy();
            webview = null;
        }
    }

    @Override
    protected void initializeDependence() {
        component = AppContext.getInstance().appComponent().gankComponent(getActModule());
    }

    @Override
    protected void initializDisplay() {
        display = new GankDisplay(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_ui_detail_web;
    }
}
