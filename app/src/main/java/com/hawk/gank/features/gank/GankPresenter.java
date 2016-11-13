package com.hawk.gank.features.gank;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hawk.gank.model.gank.Gank;
import com.hawk.gank.model.gank.GankRepo;
import com.hawk.lib.base.model.type.ListItem;
import com.hawk.lib.mvp.rx.BaseRxPresenter;
import com.hawk.lib.mvp.ui.view.BaseView;
import com.hawk.lib.mvp.util.Preconditions;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by heyong on 2016/11/6.
 */

public class GankPresenter<V extends BaseView<GankUiCallbacks>> extends BaseRxPresenter<V, GankUiCallbacks> {
    private final GankRepo mGankRepo;

    @Inject
    GankPresenter(final GankRepo gankRepo) {
        super();
        mGankRepo = gankRepo;
    }

    @Override
    protected void onInited() {
        super.onInited();

    }

    @Override
    protected void onSuspended() {
        super.onSuspended();
    }

    @Override
    protected GankUiCallbacks createUiCallbacks(V view) {
        return new GankUiCallbacks() {
            @Override
            public void showGankDetail(Gank gank, Bundle bundle) {
                Preconditions.checkNotNull(gank, "gank cannot be null");

                GankDisplay display = (GankDisplay) getDisplay();
                if (!TextUtils.isEmpty(gank._id())) {
                    display.showGankDetail(gank._id(), bundle);
                }

            }
        };
    }

    @Override
    protected void onViewAttached(V view) {
        if(view instanceof GankView) {
            GankQueryType queryType = ((GankView) view).getGankQueryType();

            int viewId = getId(view);
            switch (queryType) {
                case LIST:
                    fetchGankListIfNeeded(viewId);
                    break;
            }
        }
    }

    @Override
    protected void populateView(V view) {

        if(view instanceof GankTabView) {
            populateTabView((GankTabView)view);
        }
    }

    private void fetchGankListIfNeeded(int viewId) {
        addUtilStop(mGankRepo.getMMData(0)
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::from)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .subscribe());
    }

    private void populateTabView(GankTabView view) {
        view.setTabs(GankTab.ANDROID, GankTab.IOS, GankTab.EXPAND);
    }


    public enum GankTab {
        ANDROID, IOS, WELFARE, VIDEO, FROANT, EXPAND
    }

    public interface GankTabView extends GankView {
        void setTabs(GankTab... tabs);
    }

    public interface BaseGankListView<T extends Parcelable> extends GankView {
        void setItems(List<ListItem<T>> items);
    }

    public interface GankListView extends BaseGankListView<Gank> {

    }

    public static enum GankQueryType {
        TAB, LIST
    }

}
