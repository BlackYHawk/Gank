package com.hawk.gank.model.qualifier;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.hawk.gank.model.qualifier.CollectType.DELETE;
import static com.hawk.gank.model.qualifier.CollectType.INSERT;
import static com.hawk.gank.model.qualifier.CollectType.QUERY;

/**
 * Created by heyong on 2016/12/13.
 */
@IntDef(flag=true, value = {INSERT, DELETE, QUERY})
@Retention(RetentionPolicy.SOURCE)
public @interface CollectType {
    int INSERT = 0;
    int DELETE = 1;
    int QUERY = 2;
}
