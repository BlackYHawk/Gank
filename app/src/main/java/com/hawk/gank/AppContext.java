package com.hawk.gank;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;

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
import com.hawk.lib.base.imageloader.BigImageViewer;
import com.hawk.lib.base.imageloader.FrescoImageLoader;
import com.hawk.lib.base.model.util.UtilModule;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.loader.app.DefaultApplicationLike;

import timber.log.Timber;


/**
 * Created by heyong on 16/7/10.
 */
public class AppContext extends DefaultApplicationLike implements IApplicatioin {

    private static Application _context;
    private static AppContext _appContext;
    private AppComponent appComponent;

    public AppContext(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag,
                      long applicationStartElapsedTime, long applicationStartMillisTime,
                      Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Bugly.init(getApplication(), "73c27fe5b1", false);
        _context = getApplication();
        _appContext = this;

        FreelineCore.init(_context);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(_context, "7ahgYGrjijmpfhgrTa4s0jX0-gzGzoHsz","e3LVEnDLFUVcjNKCCE16lxQz");
        Fresco.initialize(_context, createFrescoConfig());
        BigImageViewer.initialize(FrescoImageLoader.with(_context));

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initialize(Stetho.newInitializerBuilder(_context)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(_context))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(_context))
                    .build());
            enabledStrictMode();
        }


        appComponent = createComponent();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base);
        // TODO: 安装tinker
        Beta.installTinker(this);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallback(Application.ActivityLifecycleCallbacks callbacks) {
        getApplication().registerActivityLifecycleCallbacks(callbacks);
    }

    protected AppComponent createComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(_context))
                .utilModule(new UtilModule(_context))
                .build();
    }

    private void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    private ImagePipelineConfig createFrescoConfig() {
        DiskCacheConfig mainDiskCacheConfig = DiskCacheConfig.newBuilder(_context)
                .setBaseDirectoryPath(_context.getExternalCacheDir())
                .setBaseDirectoryName(Constant.DEFAULT_CACHE_DIR)
                .setMaxCacheSize(100 * 1024 * 1024)
                .setMaxCacheSizeOnLowDiskSpace(10 * 1024 * 1024)
                .setMaxCacheSizeOnVeryLowDiskSpace(5 * 1024 * 1024)
                .setVersion(1)
                .build();
        return ImagePipelineConfig.newBuilder(_context)
                .setDownsampleEnabled(true)
                .setMainDiskCacheConfig(mainDiskCacheConfig)
                .build();
    }

    public static AppContext getInstance() {
        return _appContext;
    }

    @NonNull
    @Override
    public AppComponent appComponent() {
        return appComponent;
    }

}
