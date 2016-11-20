package com.hawk.gank.modules;

import com.hawk.gank.features.gank.GankComponent;
import com.hawk.gank.model.GsonConfigProviderModule;
import com.hawk.lib.base.model.jwdate.JWDateModule;
import com.hawk.lib.base.model.provider.ProviderModule;
import com.hawk.lib.base.module.ActModule;
import com.hawk.lib.base.model.util.UtilModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by lan on 2016/6/29.
 */
@Singleton
@Component (
        modules = {
                AppModule.class,

                ProviderModule.class, ProviderConfigModule.class, GsonConfigProviderModule.class,

                JWDateModule.class, UtilModule.class
        }
)
public interface AppComponent {

        GankComponent gankComponent(ActModule activityModule);

}
