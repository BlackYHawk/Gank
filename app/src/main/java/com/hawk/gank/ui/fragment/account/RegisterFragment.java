package com.hawk.gank.ui.fragment.account;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.google.gson.Gson;
import com.hawk.gank.R;
import com.hawk.gank.data.entity.AccountBean;
import com.hawk.gank.data.entity.AccountType;
import com.hawk.gank.http.convert.Error;
import com.hawk.gank.interfaces.impl.ValidMinLenTextWatcher;
import com.hawk.gank.interfaces.impl.ValidPhoneTextWatcher;
import com.hawk.gank.ui.activity.account.LoginActivity;
import com.hawk.gank.ui.widget.CircleImageView;
import com.hawk.gank.util.Constant;
import com.hawk.gank.util.FileUtil;
import com.hawk.gank.util.ImageUtil;
import com.hawk.gank.util.PreferenceUtil;
import com.hawk.gank.util.UIHelper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by heyong on 16/7/23.
 */
public class RegisterFragment extends BaseAccountFragment {

    public static RegisterFragment newInstance(AccountBean bean) {
        RegisterFragment fragment = new RegisterFragment();

        if (bean != null) {
            Bundle args = new Bundle();
            args.putSerializable("bean", bean);
            fragment.setArguments(args);
        }
        return fragment;
    }


    private final String TAG = RegisterFragment.class.getSimpleName();
    private LoginActivity activity;
    private AccountBean bean;
    private static final int req_Take_PICTURE = 1;
    private static final int req_Chose_PICTURE = 2;
    private String tempFilePath;
    private String headUrl;

    @BindView(R.id.ivHead) CircleImageView ivHead;
    @BindView(R.id.textInputAccount) TextInputLayout textInputAccount;
    @BindView(R.id.textInputPassword) TextInputLayout textInputPassword;
    @BindView(R.id.editUsername) EditText editUsername;
    @BindView(R.id.editPassword) EditText editPassword;
    @BindView(R.id.btnRegister) Button btnLogin;

    private ValidPhoneTextWatcher mValidPhoneTextWatcher;
    private ValidMinLenTextWatcher mValidMinLenTextWatcher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (LoginActivity) getActivity();
        setHasOptionsMenu(true);
        titleId = R.string.register_title;

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

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        mValidPhoneTextWatcher = new ValidPhoneTextWatcher(textInputAccount);
        textInputAccount.getEditText().addTextChangedListener(mValidPhoneTextWatcher);

        mValidMinLenTextWatcher = new ValidMinLenTextWatcher(textInputPassword);
        textInputPassword.getEditText().addTextChangedListener(mValidMinLenTextWatcher);
    }

    @OnClick({R.id.ivHead, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivHead :
                showGetPictureDialog();
                break;
            case R.id.btnRegister :

                if (!mValidPhoneTextWatcher.validate()) {
                    return;
                }

                if (!mValidMinLenTextWatcher.validate()) {
                    return;
                }

                String username = mValidPhoneTextWatcher.getEditTextValue();
                String password = mValidMinLenTextWatcher.getEditTextValue();

                bean.setUsername(username);
                bean.setPassword(password);
                bean.setPhone(username);

                Subscription s = leanCloudIO.register(bean)
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(() -> setRefresh(true))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(accountBean -> {
                            setRefresh(false);

                            bean.setSessionToken(accountBean.getSessionToken());
                            getAppContext().setAccountBean(bean);
                            PreferenceUtil.setUsername(getAppContext(), username);
                            PreferenceUtil.setPassword(getAppContext(), password);
                            PreferenceUtil.setHeadPath(getAppContext(), bean.getHeadUrl());
                            finish();
                        }, throwable -> loadError(throwable));
                addSubscription(s);
                break;
        }
    }

    public void setRefresh(boolean requestDataRefresh) {
        if (!requestDataRefresh) {
            UIHelper.dismiss();
        } else {
            UIHelper.showProgress(activity, R.string.account_register_progress);
        }
    }

    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        setRefresh(false);

        if(throwable instanceof HttpException) {
            HttpException exception = (HttpException)throwable;
            Response response = exception.response();

            try {
                String errorMsg = response.errorBody().string();
                Error error = new Gson().fromJson(errorMsg, Error.class);

                UIHelper.showToast(getActivity(),error.getError());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showGetPictureDialog() {
        new AlertDialog.Builder(getActivity())
                .setItems(R.array.picChose, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:          // 拍照
                                takePhoto();
                                break;
                            case 1:         // 相册
                                chosePic();
                                break;
                            default:
                                break;
                        }
                    }
                }).show();
    }

    public void takePhoto() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = FileUtil.createFile(Constant.IMAGE_ORIGIN, String.valueOf(System.currentTimeMillis() / 1000)
                + Constant.IMAGE_FORMAT);
        if(file != null) {
            tempFilePath = file.getPath();
            Uri imageUri = Uri.fromFile(file);
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(openCameraIntent, req_Take_PICTURE);
        } else {
            UIHelper.showToast(activity, "不支持SD卡");
        }
    }

    private void chosePic() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, "请选择文件..."), req_Chose_PICTURE);
    }

    private boolean saveImageByPath(String path) {
        boolean result = false;
        Bitmap bitmap = ImageUtil.getBitmapByPath(tempFilePath);
        String picName = FileUtil.getFileName(path);

        result = ImageUtil.saveBitmap(bitmap, Constant.IMAGE, picName);

        bitmap.recycle();
        return result;
    }

    private boolean saveImageByUri(Uri uri, String path){
        boolean result = false;
        try {
            InputStream is = activity.getContentResolver().openInputStream(uri);
            byte[] datas = FileUtil.readInputStream(is);
            Bitmap bitmap = ImageUtil.getBitmapByBytes(datas);

            String picName = FileUtil.getFileName(path);
            result = ImageUtil.saveBitmap(bitmap, Constant.IMAGE, picName);
            bitmap.recycle();

            return result;
        } catch (Exception e) {

        }
        return false;
    }

    private void saveImageByData(Bundle extras, String path) {
        Bitmap bitmap = (Bitmap) extras.getParcelable("data");
        ImageUtil.saveCompressBitmap(bitmap, path);
        bitmap.recycle();
    }

    public void setFileRefresh(boolean requestDataRefresh) {
        if (!requestDataRefresh) {
            UIHelper.dismiss();
        } else {
            UIHelper.showProgress(activity, R.string.account_file_progress);
        }
    }

    private void doLoadPic(String path) {
        String picName = FileUtil.getFileName(path);
        File picFile = FileUtil.getFile(path);

        try {
            setFileRefresh(true);
            AVFile avFile = AVFile.withFile(picName, picFile);
            avFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    setFileRefresh(false);
                    if(e == null) {
                        String url = avFile.getUrl();

                        bean.setHeadUrl(url);
                        Picasso.with(activity).load(new File(path)).into(ivHead);
                    }
                    else {
                        UIHelper.showToast(activity, e.getMessage());
                    }
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == req_Take_PICTURE) {
            if(resultCode == Activity.RESULT_OK) {
                File file = FileUtil.createFile(Constant.IMAGE, String.valueOf(System.currentTimeMillis() / 1000)
                        + Constant.IMAGE_FORMAT);
                String path = file.getPath();
                boolean result = saveImageByPath(path);

                if(result) {
                    doLoadPic(path);
                }
                else {
                    UIHelper.showToast(activity, R.string.pic_error);
                }
            }
        }
        else if(requestCode == req_Chose_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                File file = FileUtil.createFile(Constant.IMAGE, String.valueOf(System.currentTimeMillis() / 1000)
                        + Constant.IMAGE_FORMAT);
                String path = file.getPath();

                Uri uri = (data != null ? data.getData() : null);
                if (uri != null) {      //不同手机存储的位置不同
                    boolean result = saveImageByUri(uri, path);

                    if(result) {
                        doLoadPic(path);
                    }
                    else {
                        UIHelper.showToast(activity, R.string.pic_error);
                    }
                }
                else {
                    Bundle extras = data.getExtras();

                    if(extras != null) {
                        saveImageByData(extras, path);

                        doLoadPic(path);
                    }
                }
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_register, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_login :
                activity.switchFragment(AccountType.LOGIN.toString(), bean);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int inflateContentView() {
        return R.layout.ac_ui_register;
    }
}
