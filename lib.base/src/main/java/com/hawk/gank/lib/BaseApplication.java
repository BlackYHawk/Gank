package com.hawk.gank.lib;

import android.app.Application;
import android.content.Context;

import com.hawk.gank.lib.module.AppComponent;
import com.hawk.gank.lib.module.AppModule;
import com.hawk.gank.lib.module.DaggerAppComponent;

/**
 * Created by heyong on 2016/9/29.
 */

public class BaseApplication extends Application {

    private Context _context;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
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

}
