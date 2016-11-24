package com.hawk.gank.features.extend;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.hawk.gank.R;
import com.hawk.gank.util.StringUtil;
import com.hawk.lib.base.ui.activity.ExtendActivity;
import com.hawk.lib.base.ui.widget.ProgressWebView;

import butterknife.BindView;

/**
 * Created by lan on 2016/8/10.
 */
public class WebActivity extends ExtendActivity {
    private static final String TAG = WebActivity.class.getSimpleName();
    private static final String js_tag = "JavaScriptInterface";
    @BindView(R.id.webview) ProgressWebView webview;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDisplayBack();

        init();
    }

    private void init() {
        url = getIntent().getStringExtra("url");

        if(StringUtil.isEmpty(url)) {
            finish();
        }
        webview.loadUrl(url);
    }

    @Override
    protected void bindView() {
        super.bindView();
        webview.setOnInteractive(onWebInteractive);
    }

    private ProgressWebView.OnWebInteractive onWebInteractive = new ProgressWebView.OnWebInteractive() {
        @Override
        public void onReceiveTitle(String title) {
            setTitle(title);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_web, menu);
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
            case R.id.close :
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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
    protected int getLayoutRes() {
        return R.layout.ac_ui_web;
    }
}
