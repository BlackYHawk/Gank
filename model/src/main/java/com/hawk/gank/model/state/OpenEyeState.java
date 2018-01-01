package com.hawk.gank.model.state;

import android.support.annotation.MainThread;

import com.hawk.gank.model.bean.entity.ItemBean;
import com.hawk.gank.model.error.RxError;
import com.hawk.lib.base.state.BaseState;
import com.hawk.lib.base.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heyong on 2016/11/15.
 */

public interface OpenEyeState extends BaseState {

    @MainThread
    void setOpenEye(int viewId, String date, List<ItemBean> itemList);

    EyePagedResult getOpenEye();

    @MainThread
    void notifyRxError(int viewId, RxError rxError);

    class EyeListChangedEvent extends UiCausedEvent {
        public EyeListChangedEvent(int callingId) {
            super(callingId);
        }
    }

    class EyeRxErrorEvent extends BaseArgumentEvent<RxError> {
        public EyeRxErrorEvent(int callingId, RxError item) {
            super(callingId, item);
        }
    }

    class EyePagedResult {
        public List<ItemBean> items;
        public String date;

        public EyePagedResult(String date) {
            items = new ArrayList<>();
            this.date = date;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            EyePagedResult that = (EyePagedResult) o;
            return ObjectUtil.equal(items, that.items);
        }

        @Override
        public int hashCode() {
            return items != null ? items.hashCode() : 0;
        }
    }

}
