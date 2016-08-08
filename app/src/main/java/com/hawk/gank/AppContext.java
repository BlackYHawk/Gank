package com.hawk.gank;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.avos.avoscloud.AVOSCloud;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.hawk.gank.data.entity.AccountBean;
import com.hawk.gank.modules.AppComponent;
import com.hawk.gank.modules.AppModule;
import com.hawk.gank.modules.DaggerAppComponent;
import com.hawk.gank.util.Constant;
import com.hawk.gank.util.FileUtil;

import java.io.File;

import cn.jiajixin.nuwa.Nuwa;

/**
 * Created by heyong on 16/7/10.
 */
public class AppContext extends Application {

    public static AppContext getInstance() {
        return (AppContext)_context;
    }

    private static Context _context;
    private AppComponent appComponent;
    private AccountBean accountBean;

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"7ahgYGrjijmpfhgrTa4s0jX0-gzGzoHsz","e3LVEnDLFUVcjNKCCE16lxQz");
        Fresco.initialize(this, createFrescoConfig());
        enabledStrictMode();
        _context = this;

        initComponent();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        File patchFile = FileUtil.getFile(Constant.PATCH_DIR, "patch.jar");
        Nuwa.init(this);
        Nuwa.loadPatch(this, patchFile.getAbsolutePath());

    }

    private void initComponent() {
        this.appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent component() {
        return appComponent;
    }

    private ImagePipelineConfig createFrescoConfig() {
        DiskCacheConfig mainDiskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(getExternalCacheDir())
                .setBaseDirectoryName("fresco cache")
                .setMaxCacheSize(100 * 1024 * 1024)
                .setMaxCacheSizeOnLowDiskSpace(10 * 1024 * 1024)
                .setMaxCacheSizeOnVeryLowDiskSpace(5 * 1024 * 1024)
                .setVersion(1)
                .build();
        return ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(mainDiskCacheConfig)
                .build();
    }

    private void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    public AccountBean getAccountBean() {
        return accountBean;
    }

    public void setAccountBean(AccountBean accountBean) {
        this.accountBean = accountBean;
    }

}
