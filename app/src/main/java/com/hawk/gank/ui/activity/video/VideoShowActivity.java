package com.hawk.gank.ui.activity.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.hawk.gank.R;
import com.hawk.gank.data.entity.ItemBean;
import com.hawk.gank.ui.activity.base.BaseActivity;
import com.hawk.gank.util.StringUtil;

import butterknife.BindView;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by heyong on 16/7/19.
 */
public class VideoShowActivity extends BaseActivity {
    @BindView(R.id.videoView) VideoView mVideoView;
    private MediaController mMediaController;
    private ItemBean itemBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        //必须写这个，初始化加载库文件
     //   Vitamio.isInitialized(getAppContext());
        setContentView(R.layout.ac_ui_video_show);
        initData();
    }

    private void initData() {
        itemBean = (ItemBean) getIntent().getParcelableExtra("eyeBean");//视频播放

        if (itemBean == null || StringUtil.isEmpty(itemBean.getData().getPlayUrl())) {
            finish();
        }

        mMediaController = new MediaController(this);
        mVideoView.setVideoPath(itemBean.getData().getPlayUrl());
        mVideoView.setMediaController(mMediaController);
        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(onPreparedListener);
    }

    MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            // optional need Vitamio 4.0
            mediaPlayer.setPlaybackSpeed(1.0f);
        }
    };
}
