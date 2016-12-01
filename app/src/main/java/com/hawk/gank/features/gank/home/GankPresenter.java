package com.hawk.gank.features.gank.home;

import android.support.annotation.NonNull;

import com.hawk.gank.model.gank.Gank;
import com.hawk.gank.model.gank.GankRepo;
import com.hawk.gank.model.gank.Tag;
import com.hawk.gank.model.state.GankState;
import com.hawk.gank.util.StringUtil;
import com.hawk.lib.base.model.type.ListItem;
import com.hawk.lib.base.util.ObjectUtil;
import com.hawk.lib.mvp.rx.BaseRxPresenter;
import com.hawk.lib.mvp.ui.view.BaseView;
import com.hawk.lib.mvp.util.Preconditions;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import static com.hawk.gank.features.gank.home.GankPresenter.GankQueryType.TAB;

/**
 * Created by heyong on 2016/11/6.
 */

public class GankPresenter<V extends BaseView<GankUiCallbacks>> extends BaseRxPresenter<V, GankUiCallbacks> {
    private final GankState mGankState;
    private final GankRepo mGankRepo;

    private AtomicBoolean mTypeFromDb = new AtomicBoolean(false);

    @Inject
    GankPresenter(final GankRepo gankRepo, final GankState gankState) {
        super();
        mGankRepo = gankRepo;
        mGankState = gankState;
    }

    @Subscribe
    public void onGankTabChanged(GankState.GankTabEvent event) {
        V view = findUiFromQueryType(TAB);

        if(view != null) {
            populateView(view);
        }
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

    public void onGankFabClick() {
        for (V view : getViews()) {
            if (view instanceof GankListView) {
                ((GankListView) view).scrollToTop();
            }
        }
    }

    @Override
    protected GankUiCallbacks createUiCallbacks(V view) {
        return new GankUiCallbacks() {
            @Override
            public void finish() {
                GankDisplay display = (GankDisplay) getDisplay();
                display.finish();
            }

            @Override
            public void showGankTag() {
                GankDisplay display = (GankDisplay) getDisplay();
                display.showGankTag();
            }

            @Override
            public void showGankWeb(String url) {
                Preconditions.checkNotNull(url, "url cannot be null");

                GankDisplay display = (GankDisplay) getDisplay();
                display.showGankWeb(url);
            }

            @Override
            public void updateTag(Tag tag) {
                mGankRepo.updateTag(tag);
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
                        case FROANT :
                            fetchFrontList(getId(view), 1);
                            break;
                        case EXPAND :
                            fetchExpandList(getId(view), 1);
                            break;
                        case VIDEO :
                            fetchVideoList(getId(view), 1);
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
                        case FROANT :
                            GankState.MoviePagedResult front = mGankState.getGankFront();
                            if(front != null) {
                                fetchFrontList(getId(view), front.page + 1);
                            }
                            break;
                        case EXPAND :
                            GankState.MoviePagedResult expand = mGankState.getGankExpand();
                            if(expand != null) {
                                fetchExpandList(getId(view), expand.page + 1);
                            }
                            break;
                        case VIDEO :
                            GankState.MoviePagedResult video = mGankState.getGankVideo();
                            if(video != null) {
                                fetchVideoList(getId(view), video.page + 1);
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
                case TAB:
                case TAGLIST:
                    fetchTabIfNeeded(viewId);
                    break;
                case ANDROID:
                    fetchAndroidListIfNeeded(viewId, 1);
                    break;
                case IOS:
                    fetchIosListIfNeeded(viewId, 1);
                    break;
                case WELFARE:
                    fetchWelfareListIfNeeded(viewId, 1);
                    break;
                case FROANT:
                    fetchFrontListIfNeeded(viewId, 1);
                    break;
                case EXPAND:
                    fetchExpandListIfNeeded(viewId, 1);
                    break;
                case VIDEO:
                    fetchVideoListIfNeeded(viewId, 1);
                    break;
            }

            final GankDisplay display = (GankDisplay) getDisplay();
            if (!view.isModal()) {
                display.showUpNavigation(queryType.showUpNavigation());
            }
        }
    }

    @Override
    protected void populateView(V view) {

        if(view instanceof GankTabView) {
            populateTabView((GankTabView)view);
        } else if(view instanceof GankListView) {
            populateListView((GankListView)view);
        } else if(view instanceof TagListView) {
            populateTagListView((TagListView) view);
        }
    }

    private void fetchTabIfNeeded(@NonNull int viewId) {
        if (ObjectUtil.isEmpty(mGankState.getTagList())) {
            fetchTabTypeList(viewId);
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

    private void fetchFrontListIfNeeded(@NonNull int viewId, @NonNull int page) {
        if (ObjectUtil.isEmpty(mGankState.getGankFront())) {
            fetchFrontList(viewId, page);
        }
    }

    private void fetchExpandListIfNeeded(@NonNull int viewId, @NonNull int page) {
        if (ObjectUtil.isEmpty(mGankState.getGankExpand())) {
            fetchExpandList(viewId, page);
        }
    }

    private void fetchVideoListIfNeeded(@NonNull int viewId, @NonNull int page) {
        if (ObjectUtil.isEmpty(mGankState.getGankVideo())) {
            fetchVideoList(viewId, page);
        }
    }

    private void fetchTabTypeList(@NonNull int viewId) {
        addUtilDestroy(mGankRepo.getTagList());
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

    private void fetchFrontList(@NonNull int viewId, @NonNull int page) {
        addUtilStop(mGankRepo.getFrontData(viewId, page));
    }

    private void fetchExpandList(@NonNull int viewId, @NonNull int page) {
        addUtilStop(mGankRepo.getExpandData(viewId, page));
    }

    private void fetchVideoList(@NonNull int viewId, @NonNull int page) {
        addUtilStop(mGankRepo.getVideoData(viewId, page));
    }

    private void populateTabView(GankTabView view) {
        if(!ObjectUtil.isEmpty(mGankState.getTagList())) {
            List<Tag> tagList = mGankState.getTagList();
            List<GankTab> tabList = new ArrayList<>();

            for (int i=0; i<tagList.size(); i++) {
                if(tagList.get(i).valid()) {
                    tabList.add(StringUtil.getGankTab(tagList.get(i).type()));
                }
            }

            view.setTabs(tabList);
        }
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
            case FROANT:
                GankState.MoviePagedResult front = mGankState.getGankFront();
                if (front != null) {
                    items = front.items;
                }
                break;
            case EXPAND:
                GankState.MoviePagedResult expand = mGankState.getGankExpand();
                if (expand != null) {
                    items = expand.items;
                }
                break;
            case VIDEO:
                GankState.MoviePagedResult video = mGankState.getGankVideo();
                if (video != null) {
                    items = video.items;
                }
                break;
        }

        if (ObjectUtil.isEmpty(items)) {
            view.setItems(null);
        } else {
            view.setItems(items);
        }
    }

    private void populateTagListView(TagListView view) {
        final GankQueryType queryType = view.getGankQueryType();

        List<Tag> items = null;

        switch (queryType) {
            case TAGLIST:
                items = mGankState.getTagList();
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
        void setTabs(List<GankTab> tabList);
    }

    public interface BaseGankListView<T> extends GankView {
        void setItems(List<T> items);
        void scrollToTop();
    }

    public interface GankListView extends BaseGankListView<Gank> {}

    public interface TagListView extends BaseGankListView<Tag> {}

    public enum GankQueryType {
        TAB, ANDROID, IOS, WELFARE, VIDEO, FROANT, EXPAND, TAGLIST;

        public boolean showUpNavigation() {
            switch (this) {
                case TAGLIST:
                    return true;
                default:
                    return false;
            }
        }
    }

}
