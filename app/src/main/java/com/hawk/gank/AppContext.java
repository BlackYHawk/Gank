package com.hawk.gank;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.antfortune.freeline.FreelineCore;
import com.avos.avoscloud.AVOSCloud;
import com.hawk.gank.data.entity.AccountBean;
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
    private AccountBean accountBean;

    @Override
    public void onCreate() {
        super.onCreate();
        FreelineCore.init(this);

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"7ahgYGrjijmpfhgrTa4s0jX0-gzGzoHsz","e3LVEnDLFUVcjNKCCE16lxQz");
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

    public AccountBean getAccountBean() {
        return accountBean;
    }

    public void setAccountBean(AccountBean accountBean) {
        this.accountBean = accountBean;
    }

}
