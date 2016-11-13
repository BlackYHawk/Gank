package com.hawk.gank.model.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by heyong on 16/7/18.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface GankScope {
}
