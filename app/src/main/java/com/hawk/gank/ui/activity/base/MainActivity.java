package com.hawk.gank.ui.activity.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hawk.gank.R;
import com.hawk.gank.data.entity.AccountBean;
import com.hawk.gank.data.entity.MenuBean;
import com.hawk.gank.http.convert.Error;
import com.hawk.gank.ui.activity.account.InfoActivity;
import com.hawk.gank.ui.activity.account.LoginActivity;
import com.hawk.gank.ui.fragment.base.BaseFragment;
import com.hawk.gank.ui.fragment.gank.GankListFragment;
import com.hawk.gank.ui.fragment.openeye.EyeListFragment;
import com.hawk.gank.ui.widget.CircleImageView;
import com.hawk.gank.util.MenuGenerator;
import com.hawk.gank.util.PreferenceUtil;
import com.hawk.gank.util.UIHelper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by lan on 2016/6/29.
 */
public class MainActivity extends BaseActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    public static final String FRAGMENT_TAG = "MainFragment";
    @BindView(R.id.drawLayout) DrawerLayout mDrawer;
    @BindView(R.id.navigationView) NavigationView mNavigationView;
    private CircleImageView ivHead;
    private TextView tvName;
    private ActionBarDrawerToggle drawerToggle;
    private AccountBean accountBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.e(TAG, "onCreate");
        setContentView(R.layout.ac_ui_main);

        initView();
        onMenuClick(MenuGenerator.generateMenu(R.id.menu_pic));
    }

    @Override
    protected void handleIntent(Intent intent) {
        super.handleIntent(intent);
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

        View headerView = mNavigationView.getHeaderView(0);
        ivHead = (CircleImageView) headerView.findViewById(R.id.ivHead);
        tvName = (TextView) headerView.findViewById(R.id.tvName);
        ivHead.setOnClickListener(onClickListener);
        mNavigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            if(mDrawer.isDrawerOpen(GravityCompat.START)) {
                mDrawer.closeDrawer(GravityCompat.START);
            }

            item.setChecked(true);
            onMenuClick(MenuGenerator.generateMenu(item.getItemId()));
            return true;
        }
    };

    private void onMenuClick(MenuBean item) {
        BaseFragment fragment = null;
        switch (item.menuId) {
            case R.id.menu_pic :
                fragment = GankListFragment.newInstance();
                break;
            case R.id.menu_video :
                fragment = EyeListFragment.newInstance();
                break;
        }
        if(fragment == null) {
            return;
        }
        setDisplayTitle(item.titleRes);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment, FRAGMENT_TAG).commit();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ivHead :
                    if(getAppContext().getAvUser() == null) {
                        LoginActivity.login(MainActivity.this, accountBean);
                    }
                    else {
                        InfoActivity.info(MainActivity.this, accountBean);
                    }
                    break;
            }
        }
    };

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
        super.onResume();
        logger.e(TAG, "onResume");

        accountBean = getAppContext().getAccountBean();
        if(accountBean != null) {
            tvName.setText(accountBean.getUsername());
        }
        else {
            String username = PreferenceUtil.getUsername(getAppContext());
            String password = PreferenceUtil.getPassword(getAppContext());

            if(username != null && password != null) {
                Subscription s = leanCloudIO.login(username, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(avUser -> {
                            tvName.setText(username);

                            accountBean = new AccountBean();
                            accountBean.setUsername(username);
                            accountBean.setPassword(password);
                            accountBean.setSessionToken(avUser.getSessionToken());
                            getAppContext().setAccountBean(accountBean);
                            getAppContext().setAvUser(avUser);
                        }, throwable -> loadError(throwable));
            }
        }
        String path = PreferenceUtil.getHeadPath(getAppContext());
        if(path != null) {
            getAppContext().setHeadPath(path);
            Picasso.with(this).load(new File(path)).into(ivHead);
        }
    }


    private void loadError(Throwable throwable) {
        throwable.printStackTrace();

        if(throwable instanceof HttpException) {
            HttpException exception = (HttpException)throwable;
            Response response = exception.response();

            try {
                String errorMsg = response.errorBody().string();
                Error error = new Gson().fromJson(errorMsg, Error.class);

                UIHelper.showToast(this, error.getError());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        logger.e(TAG, "onDestroy");
        super.onDestroy();
    }

}
