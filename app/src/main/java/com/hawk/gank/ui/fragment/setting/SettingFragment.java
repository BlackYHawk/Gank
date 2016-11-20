package com.hawk.gank.ui.fragment.setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.hawk.gank.R;
import com.hawk.gank.ui.activity.base.BaseActivity;
import com.hawk.gank.ui.fragment.base.BaseFragment;
import com.hawk.lib.base.util.ObjectUtil;
import com.hawk.gank.util.UIHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by heyong on 16/9/11.
 */

public class SettingFragment extends BaseFragment {
    private final String TAG = SettingFragment.class.getSimpleName();
    private BaseActivity activity;
    @BindView(R.id.layTheme) RelativeLayout layTheme;

    private String[] skinFiles;
    private String[] skinName;


    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (BaseActivity) getActivity();

        initData();
    }

    private void initData() {
/*        try {
            skinFiles = activity.getAssets().list(SkinConfig.SKIN_DIR_NAME);

            if(ObjectUtil.isEmpty(skinFiles)) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private DialogInterface.OnClickListener OnSkinClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String skinName = skinFiles[which];

/*            SkinManager.getInstance().loadSkin(skinName, new ILoaderListener() {
                @Override
                public void onStart() {
                    Log.i("ILoaderListener", "正在切换中");

                }

                @Override
                public void onSuccess() {
                    Log.i("ILoaderListener", "切换成功");
                }

                @Override
                public void onFailed(String errMsg) {
                    Log.i("ILoaderListener", "切换失败:" + errMsg);

                }

                @Override
                public void onProgress(int progress) {
                    Log.i("ILoaderListener", "皮肤文件下载中:" + progress);

                }
            });*/
        }
    };

    @OnClick(R.id.layTheme)
    void onSettingClick(View view) {
        switch (view.getId()) {
            case R.id.layTheme :
                if(!ObjectUtil.isEmpty(skinFiles)) {
                    UIHelper.showListDialog(activity, skinFiles, OnSkinClick);
                }
                break;
        }
    }


   // @Override
    protected int inflateContentView() {
        return R.layout.ac_ui_setting;
    }

}
