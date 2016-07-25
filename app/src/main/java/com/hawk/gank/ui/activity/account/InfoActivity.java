package com.hawk.gank.ui.activity.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.hawk.gank.R;
import com.hawk.gank.data.entity.AccountBean;
import com.hawk.gank.ui.activity.base.BaseActivity;
import com.hawk.gank.util.PreferenceUtil;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;

/**
 * Created by heyong on 16/7/24.
 */
public class InfoActivity extends BaseActivity {

    /**
     * 个人信息界面
     * @param activity
     */
    public static void info(Activity activity, AccountBean bean) {
        Intent intent = new Intent(activity, InfoActivity.class);
        if (bean != null)
            intent.putExtra("bean", bean);
        activity.startActivity(intent);
    }

    private final String TAG = InfoActivity.class.getSimpleName();
    private AccountBean bean;

    @BindView(R.id.ivheadBg) ImageView ivheadBg;
    @BindView(R.id.tvName) TextView tvName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.e(TAG, "onCreate");
        setContentView(R.layout.ac_ui_info);
        setDisplayBack();
        setDisplayTitle(R.string.info_title);

        if(savedInstanceState == null) {
            bean = (AccountBean) getIntent().getSerializableExtra("bean");
        }

        initView();
    }

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
    }

    private void initView() {
        String path = getAppContext().getHeadPath();
        if(path != null) {
            Picasso.with(this).load(new File(path)).into(ivheadBg);
        }

        tvName.setText(bean.getUsername());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
                break;
            case R.id.menu_info :
                getAppContext().setAccountBean(null);
                getAppContext().setAvUser(null);
                getAppContext().setHeadPath(null);
                PreferenceUtil.setPassword(getAppContext(), null);
                PreferenceUtil.setHeadPath(getAppContext(), null);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
