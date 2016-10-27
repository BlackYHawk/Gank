package com.hawk.gank.mvp.util;

/**
 * Created by heyong on 2016/10/1.
 */

public class Preconditions {

    public static void checkNotNull(Object object, String errorMsg) {
        if(object == null) {
            throw new NullPointerException(errorMsg);
        }
    }

    public static void checkState(boolean state, String errorMsg) {
        if(!state) {
            throw new IllegalStateException(errorMsg);
        }
    }

    public static void checkArgument(boolean state, String errorMsg) {
        if(!state) {
            throw new IllegalStateException(errorMsg);
        }
    }


}
