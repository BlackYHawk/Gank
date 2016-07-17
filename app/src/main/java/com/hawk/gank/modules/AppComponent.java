package com.hawk.gank.modules;

import android.content.Context;

import com.hawk.gank.modules.library.NetworkModule;
import com.hawk.gank.modules.library.StateModule;
import com.hawk.gank.modules.library.UtilModule;
import com.hawk.gank.qualifiers.ApplicationContext;

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
