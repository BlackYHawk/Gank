package com.hawk.gank;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.NonNull;

import com.antfortune.freeline.FreelineCore;
import com.avos.avoscloud.AVOSCloud;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.stetho.Stetho;
import com.hawk.gank.modules.AppComponent;
import com.hawk.gank.modules.AppModule;
import com.hawk.gank.modules.DaggerAppComponent;
import com.hawk.gank.modules.IApplicatioin;
import com.hawk.gank.util.Constant;
import com.hawk.lib.base.model.util.UtilModule;

import timber.log.Timber;


/**
 * Created by heyong on 16/7/10.
 */
public class AppContext extends Application implements IApplicatioin {

    private static Context _context;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        FreelineCore.init(this);
        _context = this;
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"7ahgYGrjijmpfhgrTa4s0jX0-gzGzoHsz","e3LVEnDLFUVcjNKCCE16lxQz");
        Fresco.initialize(this, createFrescoConfig());

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build());
            enabledStrictMode();
        }


        appComponent = createComponent();
    }

    protected AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .utilModule(new UtilModule(this))
                .build();
    }

    private void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    private ImagePipelineConfig createFrescoConfig() {
        DiskCacheConfig mainDiskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryPath(getExternalCacheDir())
                .setBaseDirectoryName(Constant.DEFAULT_CACHE_DIR)
                .setMaxCacheSize(100 * 1024 * 1024)
                .setMaxCacheSizeOnLowDiskSpace(10 * 1024 * 1024)
                .setMaxCacheSizeOnVeryLowDiskSpace(5 * 1024 * 1024)
                .setVersion(1)
                .build();
        return ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .setMainDiskCacheConfig(mainDiskCacheConfig)
                .build();
    }

    public static AppContext getInstance() {
        return (AppContext)_context;
    }

    @NonNull
    @Override
    public AppComponent appComponent() {
        return appComponent;
    }

}
