package com.hawk.gank.features.gank.detail;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.hawk.gank.AppContext;
import com.hawk.gank.R;
import com.hawk.gank.util.StringUtil;
import com.hawk.lib.base.imageloader.FrescoImageViewFactory;
import com.hawk.lib.base.imageloader.ProgressPieIndicator;
import com.hawk.lib.base.imageloader.view.BigImageView;
import com.hawk.lib.base.ui.activity.ExtendActivity;

import butterknife.BindView;

/**
 * Created by heyong on 2016/12/6.
 */

public class DetailWelfareActivity extends ExtendActivity<DetailPresenter, DetailComponent> {
    private static final String TAG = DetailWelfareActivity.class.getSimpleName();
    @BindView(R.id.bigImageView)
    BigImageView mImageView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.gank_detail_wealfare));
        setDisplayBack();

        init();
    }

    private void init() {
        url = getIntent().getStringExtra("url");

        if(StringUtil.isEmpty(url)) {
            finish();
        }

        mImageView.setImageViewFactory(new FrescoImageViewFactory());
        mImageView.setProgressIndicator(new ProgressPieIndicator());
        mImageView.showImage(Uri.parse(url));
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu_detail_wealfare, menu);
//        return true;
//    }

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

    @Override
    protected void initializeDependence() {
        component = AppContext.getInstance().appComponent().detailComponent(getActModule());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_ui_detail_welfare;
    }
}
