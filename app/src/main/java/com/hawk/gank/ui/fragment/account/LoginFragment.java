package com.hawk.gank.ui.fragment.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hawk.gank.R;
import com.hawk.gank.model.bean.entity.AccountBean;
import com.hawk.gank.model.bean.entity.AccountType;
import com.hawk.gank.interfaces.impl.ValidMinLenTextWatcher;
import com.hawk.gank.interfaces.impl.ValidPhoneTextWatcher;
import com.hawk.gank.ui.activity.account.LoginActivity;
import com.hawk.gank.ui.widget.CircleImageView;
import com.hawk.lib.base.util.UIHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by heyong on 16/7/23.
 */
public class LoginFragment extends BaseAccountFragment {

    public static LoginFragment newInstance(AccountBean bean) {
        LoginFragment fragment = new LoginFragment();

        if (bean != null) {
            Bundle args = new Bundle();
            args.putSerializable("bean", bean);
            fragment.setArguments(args);
        }
        return fragment;
    }

    private final String TAG = LoginFragment.class.getSimpleName();
    private LoginActivity activity;
    private AccountBean bean;

    @BindView(R.id.ivHead) CircleImageView ivHead;
    @BindView(R.id.textInputAccount) TextInputLayout textInputAccount;
    @BindView(R.id.textInputPassword) TextInputLayout textInputPassword;
    @BindView(R.id.editUsername) EditText editUsername;
    @BindView(R.id.editPassword) EditText editPassword;
    @BindView(R.id.btnLogin) Button btnLogin;

    private ValidPhoneTextWatcher mValidPhoneTextWatcher;
    private ValidMinLenTextWatcher mValidMinLenTextWatcher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (LoginActivity) getActivity();
        setHasOptionsMenu(true);
    //    titleId = R.string.login_title;

        if (savedInstanceState == null) {
            if (getArguments() != null)
                bean = (AccountBean) getArguments().getSerializable("bean");
        }
        else {
            bean = (AccountBean) savedInstanceState.getSerializable("bean");
        }
        initData();
    }

    private void initData() {
        if (bean == null) {
            bean = new AccountBean();
        }
    }

  //  @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        mValidPhoneTextWatcher = new ValidPhoneTextWatcher(textInputAccount);
        textInputAccount.getEditText().addTextChangedListener(mValidPhoneTextWatcher);

        mValidMinLenTextWatcher = new ValidMinLenTextWatcher(textInputPassword);
        textInputPassword.getEditText().addTextChangedListener(mValidMinLenTextWatcher);
    }

    @OnClick(R.id.btnLogin)
    public void onClick(View view) {
        if (!mValidPhoneTextWatcher.validate()) {
            return;
        }

        if (!mValidMinLenTextWatcher.validate()) {
            return;
        }

        String username = mValidPhoneTextWatcher.getEditTextValue();
        String password = mValidMinLenTextWatcher.getEditTextValue();
//
//        Subscription s = leanCloudIO.login(username, password)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(() -> setRefresh(true))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(accountBean -> {
//                    setRefresh(false);
//
//                    bean.setUsername(accountBean.getUsername());
//                    bean.setPassword(password);
//                    bean.setSessionToken(accountBean.getSessionToken());
//                    bean.setHeadUrl(accountBean.getHeadUrl());
//                    getAppContext().setAccountBean(bean);
//                    PreferenceUtil.setUsername(getAppContext(), username);
//                    PreferenceUtil.setPassword(getAppContext(), password);
//                    PreferenceUtil.setHeadPath(getAppContext(), accountBean.getHeadUrl());
//
//                    finish();
//                }, throwable -> loadError(throwable));
//        addSubscription(s);
    }

    public void setRefresh(boolean requestDataRefresh) {
        if (!requestDataRefresh) {
            UIHelper.dismiss();
        } else {
            UIHelper.showProgress(activity, R.string.account_login_progress);
        }
    }
//
//    private void loadError(Throwable throwable) {
//        throwable.printStackTrace();
//        setRefresh(false);
//
//        if(throwable instanceof HttpException) {
//            HttpException exception = (HttpException)throwable;
//            Response response = exception.response();
//
//            try {
//                String errorMsg = response.errorBody().string();
//                Error error = new Gson().fromJson(errorMsg, Error.class);
//
//                UIHelper.showToast(getActivity(),error.getError());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_login, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_register :
                activity.switchFragment(AccountType.REGISTER.toString(), bean);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
//
//    @Override
//    protected int inflateContentView() {
//        return R.layout.ac_ui_login;
//    }
}
