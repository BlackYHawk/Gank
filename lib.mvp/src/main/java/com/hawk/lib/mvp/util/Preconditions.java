package com.hawk.lib.mvp.util;

/**
 * Created by heyong on 2016/10/1.
 */

public class Preconditions {

    public static <T> T checkNotNull(T object, String errorMsg) {
        if(object == null) {
            throw new NullPointerException(errorMsg);
        }
        return object;
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
