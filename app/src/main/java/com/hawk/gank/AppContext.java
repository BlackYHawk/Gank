package com.hawk.gank;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.hawk.gank.modules.AppComponent;
import com.hawk.gank.modules.AppModule;
import com.hawk.gank.modules.DaggerAppComponent;

/**
 * Created by heyong on 16/7/10.
 */
public class AppContext extends Application {

    public static AppContext getInstance() {
        return (AppContext)_context;
    }

    private static Context _context;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        enabledStrictMode();
        _context = this;

        initComponent();
    }

    private void initComponent() {
        this.appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent component() {
        return appComponent;
    }

    private void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }
}
