package com.hawk.gank.lib.state;

/**
 * Created by heyong on 2016/10/4.
 */

public interface BaseState {

    void registerForEvents(Object receiver);

    void unregisterForEvents(Object receiver);

    class UiCausedEvent {
        public final int callingId;

        public UiCausedEvent(int callingId) {
            this.callingId = callingId;
        }
    }

}
