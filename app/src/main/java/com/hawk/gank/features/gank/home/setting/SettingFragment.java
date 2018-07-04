package com.hawk.gank.features.gank.home.setting;

import android.content.DialogInterface;
import android.view.View;
import android.widget.RelativeLayout;

import com.hawk.gank.R;
import com.hawk.gank.features.gank.home.GankPresenter;
import com.hawk.gank.features.gank.home.GankUiCallbacks;
import com.hawk.gank.features.gank.home.GankView;
import com.hawk.lib.base.ui.fragment.BaseFragment;
import com.hawk.lib.base.util.ObjectUtil;
import com.hawk.lib.base.util.UIHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by heyong on 2018/1/1.
 */

public class SettingFragment extends BaseFragment<GankView, GankUiCallbacks,
        GankPresenter<GankView>> {
    @BindView(R.id.layTheme) RelativeLayout layTheme;
    private String[] skinFiles;
    private String[] skinName;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    protected void onBindData() {
/*        try {
            skinFiles = activity.getAssets().list(SkinConfig.SKIN_DIR_NAME);

            if(ObjectUtil.isEmpty(skinFiles)) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @OnClick(R.id.layTheme)
    void onSettingClick(View view) {
        switch (view.getId()) {
            case R.id.layTheme :
                if(!ObjectUtil.isEmpty(skinFiles)) {
                    UIHelper.showListDialog(getActivity(), skinFiles, OnSkinClick);
                }
                break;
        }
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

    @Override
    protected int getLayoutRes() {
        return R.layout.ac_ui_setting;
    }

}
