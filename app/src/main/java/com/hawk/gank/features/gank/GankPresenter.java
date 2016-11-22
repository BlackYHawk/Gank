package com.hawk.gank.features.gank;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hawk.gank.model.gank.Gank;
import com.hawk.gank.model.gank.GankRepo;
import com.hawk.gank.model.state.GankState;
import com.hawk.lib.base.model.type.ListItem;
import com.hawk.lib.base.util.ObjectUtil;
import com.hawk.lib.mvp.rx.BaseRxPresenter;
import com.hawk.lib.mvp.ui.view.BaseView;
import com.hawk.lib.mvp.util.Preconditions;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by heyong on 2016/11/6.
 */

public class GankPresenter<V extends BaseView<GankUiCallbacks>> extends BaseRxPresenter<V, GankUiCallbacks> {
    private final GankState mGankState;
    private final GankRepo mGankRepo;

    @Inject
    GankPresenter(final GankRepo gankRepo, final GankState gankState) {
        super();
        mGankRepo = gankRepo;
        mGankState = gankState;
    }

    @Subscribe
    public void onGankListChanged(GankState.GankListChangedEvent event) {
        V view = findView(event.callingId);

        if(view != null) {
            populateView(view);
        }
        else {
            populateViews();
        }
    }

    @Subscribe
    public void onGankRxError(GankState.GankRxErrorEvent event) {
        V view = findView(event.callingId);

        if(view != null && event.item != null) {
            if (view instanceof GankView) {
                ((GankView) view).showError(event.item);
            }
        }
    }

    @Override
    protected void onInited() {
        super.onInited();
        mGankState.registerForEvent(this);
    }

    @Override
    protected void onSuspended() {
        super.onSuspended();
        mGankState.unregisterForEvent(this);
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

            @Override
            public void onPulledToTop() {
                if(view instanceof GankListView) {
                    switch (((GankListView) view).getGankQueryType()) {
                        case ANDROID :
                            fetchAndroidList(getId(view), 1);
                            break;
                        case IOS :
                            fetchIosList(getId(view), 1);
                            break;
                        case WELFARE :
                            fetchWelfareList(getId(view), 1);
                            break;
                    }
                }
            }

            @Override
            public void onScrolledToBottom() {
                if(view instanceof GankListView) {
                    switch (((GankListView) view).getGankQueryType()) {
                        case ANDROID :
                            GankState.MoviePagedResult android = mGankState.getGankAndroid();
                            if(android != null) {
                                fetchAndroidList(getId(view), android.page + 1);
                            }
                            break;
                        case IOS :
                            GankState.MoviePagedResult ios = mGankState.getGankIos();
                            if(ios != null) {
                                fetchIosList(getId(view), ios.page + 1);
                            }
                            break;
                        case WELFARE :
                            GankState.MoviePagedResult welfare = mGankState.getGankWelfare();
                            if(welfare != null) {
                                fetchWelfareList(getId(view), welfare.page + 1);
                            }
                            break;
                    }
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
                case ANDROID:
                    fetchAndroidListIfNeeded(viewId, 1);
                    break;
                case IOS:
                    fetchIosListIfNeeded(viewId, 1);
                    break;
                case WELFARE:
                    fetchWelfareListIfNeeded(viewId, 1);
                    break;
            }
        }
    }

    @Override
    protected void populateView(V view) {

        if(view instanceof GankTabView) {
            populateTabView((GankTabView)view);
        } else if(view instanceof GankListView) {
            populateListView((GankListView)view);
        }
    }

    private void fetchAndroidListIfNeeded(@NonNull int viewId, @NonNull int page) {
        if (ObjectUtil.isEmpty(mGankState.getGankAndroid())) {
            fetchAndroidList(viewId, page);
        }
    }

    private void fetchIosListIfNeeded(@NonNull int viewId, @NonNull int page) {
        if (ObjectUtil.isEmpty(mGankState.getGankIos())) {
            fetchIosList(viewId, page);
        }
    }

    private void fetchWelfareListIfNeeded(@NonNull int viewId, @NonNull int page) {
        if (ObjectUtil.isEmpty(mGankState.getGankWelfare())) {
            fetchWelfareList(viewId, page);
        }
    }

    private void fetchAndroidList(@NonNull int viewId, @NonNull int page) {
        addUtilStop(mGankRepo.getAndroidData(viewId, page));
    }

    private void fetchIosList(@NonNull int viewId, @NonNull int page) {
        addUtilStop(mGankRepo.getIosData(viewId, page));
    }

    private void fetchWelfareList(@NonNull int viewId, @NonNull int page) {
        addUtilStop(mGankRepo.getMMData(viewId, page));
    }

    private void populateTabView(GankTabView view) {
        view.setTabs(GankTab.ANDROID, GankTab.IOS, GankTab.WELFARE);
    }

    private void populateListView(GankListView view) {
        final GankQueryType queryType = view.getGankQueryType();

        List<Gank> items = null;

        switch (queryType) {
            case ANDROID:
                GankState.MoviePagedResult android = mGankState.getGankAndroid();
                if (android != null) {
                    items = android.items;
                }
                break;
            case IOS:
                GankState.MoviePagedResult ios = mGankState.getGankIos();
                if (ios != null) {
                    items = ios.items;
                }
                break;
            case WELFARE:
                GankState.MoviePagedResult welfare = mGankState.getGankWelfare();
                if (welfare != null) {
                    items = welfare.items;
                }
                break;
        }

        if (ObjectUtil.isEmpty(items)) {
            view.setItems(null);
        } else {
            view.setItems(items);
        }
    }

    private final void populateUiFromQueryType(GankQueryType queryType) {
        V ui = findUiFromQueryType(queryType);
        if (ui != null) {
            populateView(ui);
        }
    }

    private V findUiFromQueryType(GankQueryType queryType) {
        for (V ui : getViews()) {
            if (ui instanceof GankView) {
                if(((GankView)ui).getGankQueryType() == queryType){
                    return ui;
                }
            }
        }
        return null;
    }

    private <T extends ListItem<T>> List<ListItem<T>> createListItemList(final List<T> items) {
        Preconditions.checkNotNull(items, "items cannot be null");
        ArrayList<ListItem<T>> listItems = new ArrayList<>(items.size());
        for (ListItem<T> item : items) {
            listItems.add(item);
        }
        return listItems;
    }

    public enum GankTab {
        ANDROID, IOS, WELFARE, VIDEO, FROANT, EXPAND
    }

    public interface GankSideMenuView extends GankView {

    }

    public interface GankTabView extends GankView {
        void setTabs(GankTab... tabs);
    }

    public interface BaseGankListView<T extends Parcelable> extends GankView {
        void setItems(List<T> items);
    }

    public interface GankListView extends BaseGankListView<Gank> {

    }

    public enum GankQueryType {
        TAB, ANDROID, IOS, WELFARE, VIDEO, FROANT, EXPAND
    }

}
