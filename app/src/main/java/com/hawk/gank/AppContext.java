package com.hawk.gank;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.NonNull;

import com.antfortune.freeline.FreelineCore;
import com.avos.avoscloud.AVOSCloud;
import com.facebook.stetho.Stetho;
import com.hawk.gank.modules.AppComponent;
import com.hawk.gank.modules.AppModule;
import com.hawk.gank.modules.DaggerAppComponent;
import com.hawk.gank.modules.IApplicatioin;
import com.hawk.lib.base.util.UtilModule;


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

        if(BuildConfig.DEBUG) {
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

    public static AppContext getInstance() {
        return (AppContext)_context;
    }

    @NonNull
    @Override
    public AppComponent appComponent() {
        return appComponent;
    }

}
