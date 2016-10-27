package com.hawk.gank.lib.module;

import android.content.Context;

import com.hawk.gank.lib.module.library.NetworkModule;
import com.hawk.gank.lib.module.library.StateModule;
import com.hawk.gank.lib.module.library.UtilModule;
import com.hawk.gank.lib.qualifiers.ApplicationContext;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by lan on 2016/6/29.
 */
@Singleton
@Component (
        modules = {
                AppModule.class,
                StateModule.class,
                NetworkModule.class,
                UtilModule.class
        }
)
public interface AppComponent {

        ActComponent plus(ActModule actModule);

        @ApplicationContext
        Context appContext();

}
