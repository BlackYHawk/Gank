package com.hawk.gank.features.gank.home;

import android.support.annotation.NonNull;

import com.hawk.gank.R;
import com.hawk.gank.model.bean.Gank;
import com.hawk.gank.model.bean.Tag;
import com.hawk.gank.model.bean.entity.ItemBean;
import com.hawk.gank.model.repository.EyeRepo;
import com.hawk.gank.model.repository.GankRepo;
import com.hawk.gank.model.state.GankState;
import com.hawk.gank.model.state.OpenEyeState;
import com.hawk.gank.util.StringUtil;
import com.hawk.lib.base.model.type.ListItem;
import com.hawk.lib.base.model.util.ResDelegate;
import com.hawk.lib.base.util.ObjectUtil;
import com.hawk.lib.mvp.rx.BaseRxPresenter;
import com.hawk.lib.mvp.ui.view.BaseView;
import com.hawk.lib.mvp.util.Preconditions;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.hawk.gank.features.gank.home.GankPresenter.GankQueryType.TAB;

/**
 * Created by heyong on 2016/11/6.
 */

public class GankPresenter<V extends BaseView<GankUiCallbacks>> extends BaseRxPresenter<V, GankUiCallbacks> {
    private static final String TAG = GankPresenter.class.getSimpleName();
    private final ResDelegate mResDel;
    private final GankState mGankState;
    private final GankRepo mGankRepo;
    private final OpenEyeState mEyeState;
    private final EyeRepo mEyeRepo;
    private CompositeDisposable mDbDisposables;

    @Inject
    GankPresenter(final ResDelegate resDelegate, final GankRepo gankRepo, final GankState gankState,
                  final OpenEyeState eyeState, final EyeRepo eyeRepo) {
        super();
        mResDel = resDelegate;
        mGankRepo = gankRepo;
        mGankState = gankState;
        mEyeRepo = eyeRepo;
        mEyeState = eyeState;
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

    @Subscribe
    public void onGankCollectChanged(GankState.GankCollectEvent event) {
        GankDisplay display = (GankDisplay) getDisplay();
        display.collectGank(event.type);
    }

    @Subscribe
    public void onEyeListChanged(OpenEyeState.EyeListChangedEvent event) {
        V view = findView(event.callingId);

        if(view != null) {
            populateView(view);
        }
        else {
            populateViews();
        }
    }

    @Subscribe
    public void onEyeRxError(OpenEyeState.EyeRxErrorEvent event) {
        V view = findView(event.callingId);

        if(view != null && event.item != null) {
            if (view instanceof GankView) {
                ((GankView) view).showError(event.item);
            }
        }
    }

    @Override
    protected void onInited() {
        Timber.tag(TAG).e("onInited");
        super.onInited();
        mGankState.registerForEvent(this);
        initDbDisposables();
    }

    @Override
    protected void onSuspended() {
        Timber.tag(TAG).e("onSuspended");
        super.onSuspended();
        disposeeDb();
        mGankState.unregisterForEvent(this);
    }

    public void onGankFabClick() {
        for (V view : getViews()) {
            if (view instanceof GankListView) {
                ((GankListView) view).scrollToTop();
            }
        }
    }

    public void onGankCollectExist(@NonNull Gank gank) {
        addUtilDestroy(mGankRepo.existGank(gank._id()));
    }

    public void onGankCollectClick(boolean delete, Gank gank) {
        if (delete) {
            mGankRepo.deleteCollect(gank);
        }
        else {
            mGankRepo.collectGank(gank);
        }
    }

    private String getViewTitle(final V view) {
        if(view instanceof GankView) {
            switch (((GankView) view).getGankQueryType()) {
                case TAGLIST:
                    return mResDel.getStringRes(R.string.gank_title);
                case COLLECTLIST:
                    return mResDel.getStringRes(R.string.gank_collect_title);
                case OPENEYE:
                    return mResDel.getStringRes(R.string.gank_openeye_title);
            }
        }
        return null;
    }

    @Override
    protected GankUiCallbacks createUiCallbacks(final V view) {
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
            public void showGankWeb(Gank gank) {
                Preconditions.checkNotNull(gank, "gank cannot be null");

                GankDisplay display = (GankDisplay) getDisplay();
                display.showGankWeb(gank);
            }

            @Override
            public void showGankWealfare(String url) {
                Preconditions.checkNotNull(url, "url cannot be null");

                GankDisplay display = (GankDisplay) getDisplay();
                display.showGankImage(url);
            }

            @Override
            public void showGankCollect() {
                GankDisplay display = (GankDisplay) getDisplay();
                display.showGankCollect();
            }

            @Override
            public void showBug() {
                GankDisplay display = (GankDisplay) getDisplay();
                display.showBug();
            }

            @Override
            public void updateTag(Tag tag) {
                addUtilStop(mGankRepo.updateTag(tag));
            }

            @Override
            public void showOpenEye(ItemBean itemBean) {
                GankDisplay display = (GankDisplay) getDisplay();
                display.showOpenEye(itemBean);
            }

            @Override
            public void onPulledToTop() {
                if(view instanceof GankListView) {
                    clearDbDisposables();
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
                        case COLLECTLIST :
                            fetchCollectList(getId(view), 1);
                            break;
                        case OPENEYE :
                            String date = String.valueOf(System.currentTimeMillis());
                            fetchEyeList(getId(view), date);
                            break;
                    }
                }
            }

            @Override
            public void onScrolledToBottom() {
                if(view instanceof GankListView) {
                    clearDbDisposables();
                    switch (((GankListView) view).getGankQueryType()) {
                        case ANDROID :
                            GankState.GankPagedResult android = mGankState.getGankAndroid();
                            if(android != null) {
                                fetchAndroidList(getId(view), android.page + 1);
                            }
                            break;
                        case IOS :
                            GankState.GankPagedResult ios = mGankState.getGankIos();
                            if(ios != null) {
                                fetchIosList(getId(view), ios.page + 1);
                            }
                            break;
                        case WELFARE :
                            GankState.GankPagedResult welfare = mGankState.getGankWelfare();
                            if(welfare != null) {
                                fetchWelfareList(getId(view), welfare.page + 1);
                            }
                            break;
                        case FROANT :
                            GankState.GankPagedResult front = mGankState.getGankFront();
                            if(front != null) {
                                fetchFrontList(getId(view), front.page + 1);
                            }
                            break;
                        case EXPAND :
                            GankState.GankPagedResult expand = mGankState.getGankExpand();
                            if(expand != null) {
                                fetchExpandList(getId(view), expand.page + 1);
                            }
                            break;
                        case VIDEO :
                            GankState.GankPagedResult video = mGankState.getGankVideo();
                            if(video != null) {
                                fetchVideoList(getId(view), video.page + 1);
                            }
                            break;
                        case COLLECTLIST :
                            GankState.GankPagedResult collect = mGankState.getGankCollect();
                            if(collect != null && collect.full()) {
                                fetchCollectList(getId(view), collect.page + 1);
                            }
                            break;
                        case OPENEYE :
                            OpenEyeState.EyePagedResult result = mEyeState.getOpenEye();
                            if(result != null) {
                                String date = result.date;
                                fetchEyeList(getId(view), StringUtil.getSpecifiedDayBefore(date));
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
            String title = null;
            int viewId = getId(view);

            switch (queryType) {
                case TAB:
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
                case TAGLIST:
                    fetchTabIfNeeded(viewId);
                    title = getViewTitle(view);
                    break;
                case COLLECTLIST:
                    fetchCollectList(viewId, 1);
                    title = getViewTitle(view);
                    break;
                case OPENEYE:
                    String date = String.valueOf(System.currentTimeMillis());
                    fetchEyeIfNeeded(viewId, date);
                    title = getViewTitle(view);
                    break;
            }

            final GankDisplay display = (GankDisplay) getDisplay();
            if (!view.isModal()) {
                display.showUpNavigation(queryType.showUpNavigation());
            }
            if (!StringUtil.isEmpty(title)) {
                display.setActionBarTitle(title);
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
        } else if(view instanceof OpenEyeListView) {
            populateEyeListView((OpenEyeListView) view);
        }
    }

    private void initDbDisposables() {
        if (mDbDisposables == null) {
            mDbDisposables = new CompositeDisposable();
        }
    }

    private void addDbDisposable(Disposable disposable) {
        if (mDbDisposables != null) {
            mDbDisposables.add(disposable);
        }
    }

    private void clearDbDisposables() {
        if (mDbDisposables != null) {
            mDbDisposables.clear();
        }
    }

    private void disposeeDb() {
        if (mDbDisposables != null) {
            mDbDisposables.dispose();
            mDbDisposables = null;
        }
    }

    private void fetchTabIfNeeded(@NonNull int viewId) {
        if (ObjectUtil.isEmpty(mGankState.getTagList())) {
            fetchTabTypeList(viewId);
        }
    }

    private void fetchAndroidListIfNeeded(@NonNull int viewId, @NonNull int page) {
        if (ObjectUtil.isEmpty(mGankState.getGankAndroid()) ||
                ObjectUtil.isEmpty(mGankState.getGankAndroid().items)) {
            fetchAndroidList(viewId, page);
        }
    }

    private void fetchIosListIfNeeded(@NonNull int viewId, @NonNull int page) {
        if (ObjectUtil.isEmpty(mGankState.getGankIos()) ||
                ObjectUtil.isEmpty(mGankState.getGankIos().items)) {
            fetchIosList(viewId, page);
        }
    }

    private void fetchWelfareListIfNeeded(@NonNull int viewId, @NonNull int page) {
        if (ObjectUtil.isEmpty(mGankState.getGankWelfare()) ||
                ObjectUtil.isEmpty(mGankState.getGankWelfare().items)) {
            fetchWelfareList(viewId, page);
        }
    }

    private void fetchFrontListIfNeeded(@NonNull int viewId, @NonNull int page) {
        if (ObjectUtil.isEmpty(mGankState.getGankFront()) ||
                ObjectUtil.isEmpty(mGankState.getGankFront().items)) {
            fetchFrontList(viewId, page);
        }
    }

    private void fetchExpandListIfNeeded(@NonNull int viewId, @NonNull int page) {
        if (ObjectUtil.isEmpty(mGankState.getGankExpand()) ||
                ObjectUtil.isEmpty(mGankState.getGankExpand().items)) {
            fetchExpandList(viewId, page);
        }
    }

    private void fetchVideoListIfNeeded(@NonNull int viewId, @NonNull int page) {
        if (ObjectUtil.isEmpty(mGankState.getGankVideo()) ||
                ObjectUtil.isEmpty(mGankState.getGankVideo().items)) {
            fetchVideoList(viewId, page);
        }
    }

    private void fetchEyeIfNeeded(@NonNull int viewId, @NonNull String date) {
        if (ObjectUtil.isEmpty(mEyeState.getOpenEye()) ||
                ObjectUtil.isEmpty(mEyeState.getOpenEye().items)) {
            fetchEyeList(viewId, date);
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
        addUtilStop(mGankRepo.getWelfareData(viewId, page));
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

    private void fetchCollectList(@NonNull int viewId, @NonNull int page) {
        addUtilDestroy(mGankRepo.getCollectData(viewId, page));
    }

    private void fetchEyeList(@NonNull int viewId, @NonNull String date) {
        addUtilStop(mEyeRepo.getOpenEyeData(viewId, date));
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

        boolean scrollBottom = false;
        List<Gank> items = null;

        switch (queryType) {
            case ANDROID:
                GankState.GankPagedResult android = mGankState.getGankAndroid();
                if (android != null) {
                    items = android.items;
                    scrollBottom = android.full();
                }
                break;
            case IOS:
                GankState.GankPagedResult ios = mGankState.getGankIos();
                if (ios != null) {
                    items = ios.items;
                    scrollBottom = ios.full();
                }
                break;
            case WELFARE:
                GankState.GankPagedResult welfare = mGankState.getGankWelfare();
                if (welfare != null) {
                    items = welfare.items;
                    scrollBottom = welfare.full();
                }
                break;
            case FROANT:
                GankState.GankPagedResult front = mGankState.getGankFront();
                if (front != null) {
                    items = front.items;
                    scrollBottom = front.full();
                }
                break;
            case EXPAND:
                GankState.GankPagedResult expand = mGankState.getGankExpand();
                if (expand != null) {
                    items = expand.items;
                    scrollBottom = expand.full();
                }
                break;
            case VIDEO:
                GankState.GankPagedResult video = mGankState.getGankVideo();
                if (video != null) {
                    items = video.items;
                    scrollBottom = video.full();
                }
                break;
            case COLLECTLIST:
                GankState.GankPagedResult collect = mGankState.getGankCollect();
                if (collect != null) {
                    items = collect.items;
                    scrollBottom = collect.full();
                }
                break;
        }

        if (ObjectUtil.isEmpty(items)) {
            view.setItems(null);
        } else {
            view.enableScrollBottom(scrollBottom);
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

    private void populateEyeListView(OpenEyeListView view) {
        final GankQueryType queryType = view.getGankQueryType();

        List<ItemBean> items = null;

        switch (queryType) {
            case OPENEYE:
                OpenEyeState.EyePagedResult result = mEyeState.getOpenEye();
                if (result != null) {
                    items = result.items;
                 //   scrollBottom = video.full();
                }
                break;
        }

        if (ObjectUtil.isEmpty(items)) {
            view.setItems(null);
        } else {
         //   view.enableScrollBottom(scrollBottom);
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

    public interface GankTabView extends GankView {
        void setTabs(List<GankTab> tabList);
    }

    public interface BaseGankListView<T> extends GankView {
        void setItems(List<T> items);
        void scrollToTop();
        void enableScrollBottom(boolean enable);
    }

    public interface GankListView extends BaseGankListView<Gank> {}

    public interface TagListView extends BaseGankListView<Tag> {}

    public interface OpenEyeListView extends BaseGankListView<ItemBean> {}

    public enum GankQueryType {
        TAB, ANDROID, IOS, WELFARE, VIDEO, FROANT, EXPAND, TAGLIST, COLLECTLIST, OPENEYE;

        public boolean showUpNavigation() {
            switch (this) {
                case TAGLIST:
                    return true;
                case COLLECTLIST:
                    return true;
                default:
                    return false;
            }
        }
    }

}
