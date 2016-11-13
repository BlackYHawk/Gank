package com.hawk.lib.base.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by lan on 2016/4/25.
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationContext {
}
