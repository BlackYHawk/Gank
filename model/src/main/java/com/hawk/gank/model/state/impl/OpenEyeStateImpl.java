package com.hawk.gank.model.state.impl;

import com.hawk.gank.model.bean.entity.ItemBean;
import com.hawk.gank.model.error.RxError;
import com.hawk.gank.model.state.OpenEyeState;
import com.squareup.otto.Bus;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by heyong on 2018/1/1.
 */
@Singleton
public class OpenEyeStateImpl implements OpenEyeState {
    private final Bus mEventBus;
    private EyePagedResult mResult;

    @Inject
    public OpenEyeStateImpl(final Bus bus) {
        this.mEventBus = bus;
    }

    @Override
    public void setOpenEye(int viewId, String date, List<ItemBean> itemList) {
        if(mResult == null) {
            mResult = new EyePagedResult(date);
        }
        String currentDate = mResult.date;

        if (currentDate.compareTo(date) >= 0) {
            mResult.items.clear();
        }
        mResult.items.addAll(itemList);
        mResult.date = date;
        mEventBus.post(new EyeListChangedEvent(viewId));
    }

    @Override
    public EyePagedResult getOpenEye() {
        return mResult;
    }

    @Override
    public void notifyRxError(int viewId, RxError rxError) {
        mEventBus.post(new EyeRxErrorEvent(viewId, rxError));
    }

    @Override
    public void registerForEvent(Object receiver) {
        mEventBus.register(receiver);
    }

    @Override
    public void unregisterForEvent(Object receiver) {
        mEventBus.unregister(receiver);
    }

}
