package com.hawk.gank.model;

import com.hawk.gank.model.provider.DateTimeFormatterProvider;
import com.hawk.lib.base.model.jwdate.ThreeTenABPDelegate;
import com.hawk.lib.base.model.provider.GsonConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by heyong on 2016/11/4.
 */
@Module
public class GsonConfigProviderModule {
    private static final String TIME_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Singleton
    @Provides
    GsonConfig provideGsonConfig(final ThreeTenABPDelegate delegate) {
        delegate.init();
        return GsonConfig.builder()
                .dateFormatString(TIME_FORMAT_ISO_8601)
                .dateTimeFormatter(DateTimeFormatterProvider.provideDateTimeFormatter())
                .autoGsonTypeAdapterFactory(AutoValueGsonAdapterFactory.create())
                .build();
    }
}
