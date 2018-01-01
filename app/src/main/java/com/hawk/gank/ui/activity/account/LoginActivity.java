package com.hawk.gank.ui.activity.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.hawk.gank.R;
import com.hawk.gank.model.bean.entity.AccountBean;
import com.hawk.gank.model.bean.entity.AccountType;
import com.hawk.gank.ui.activity.base.BaseActivity;
import com.hawk.gank.ui.fragment.account.BaseAccountFragment;
import com.hawk.gank.ui.fragment.account.LoginFragment;
import com.hawk.gank.ui.fragment.account.RegisterFragment;
import com.hawk.gank.util.StringUtil;

/**
 * Created by heyong on 16/7/21.
 */
public class LoginActivity extends BaseActivity {

    /**
     * 登录界面
     * @param activity
     */
    public static void login(Activity activity, AccountBean accountBean) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra("type", AccountType.LOGIN.toString());
        if (accountBean != null)
            intent.putExtra("bean", accountBean);
        activity.startActivity(intent);
    }

    private final String TAG = LoginActivity.class.getSimpleName();
    public static final String FRAGMENT_TAG = "LoginFragment";
    private String type;
    private AccountBean bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_ui_account);

        if(savedInstanceState == null) {
            type = getIntent().getStringExtra("type");
            bean = (AccountBean) getIntent().getSerializableExtra("bean");

            if (StringUtil.isEmpty(type)) {
                finish();
                return;
            }

            switchFragment(type, bean);
        }
    }

    public void switchFragment(String type, AccountBean bean) {
        AccountType accountType = AccountType.valueOf(type);

        BaseAccountFragment fragment = null;
        switch (accountType) {
            case LOGIN:
                fragment = LoginFragment.newInstance(bean);
                break;
            case REGISTER:
                fragment = RegisterFragment.newInstance(bean);
                break;
            default:
                break;
        }

        if(fragment == null) {
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment, FRAGMENT_TAG).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
