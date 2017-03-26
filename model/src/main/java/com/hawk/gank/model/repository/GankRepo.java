package com.hawk.gank.model.repository;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.hawk.gank.model.bean.Gank;
import com.hawk.gank.model.bean.GankResult;
import com.hawk.gank.model.bean.Tag;
import com.hawk.gank.model.db.GankDbDelegate;
import com.hawk.gank.model.error.RxErrorProcessor;
import com.hawk.gank.model.http.GankIO;
import com.hawk.gank.model.qualifier.CollectType;
import com.hawk.gank.model.qualifier.GankType;
import com.hawk.gank.model.state.GankState;
import com.hawk.lib.base.util.ObjectUtil;
import com.hawk.lib.mvp.qualifiers.ActivityScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by heyong on 2016/11/7.
 */
@ActivityScope
public class GankRepo {
    public final String[] typeArray = new String[]{"Android", "iOS", "前端", "拓展资源", "福利",
            "休息视频"};
    private final GankIO mGankIO;
    private final GankDbDelegate mGankDb;
    private final GankState mGankState;
    private final RxErrorProcessor mRxErrorProcessor;

    @Inject
    GankRepo(final GankIO gankIO, final GankDbDelegate gankDb, final GankState gankState,
             final RxErrorProcessor rxErrorProcessor) {
        this.mGankIO = gankIO;
        this.mGankDb = gankDb;
        this.mGankState = gankState;
        this.mRxErrorProcessor = rxErrorProcessor;
    }

    public Disposable updateTag(final Tag tag) {
        return Flowable.create(subscriber -> {
                subscriber.onNext(mGankDb.updateTag(tag));
                subscriber.onComplete();
        }, BackpressureStrategy.BUFFER)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(ret -> mGankState.updateTag(tag));
    }

    @SuppressLint("NewApi")
    @NonNull
    public Disposable getTagList() {
        return RxJavaInterop.toV2Flowable(mGankDb.getTagList())
                .subscribeOn(Schedulers.io())
                .map(tags -> {
                             if(ObjectUtil.isEmpty(tags)) {
                                 List<String> typeList = Arrays.asList(typeArray);
                                 List<Tag> tagList = new ArrayList<>();

                                 for (String type : typeList) {
                                     Tag tag = Tag.builder().type(type).valid(true).build();

                                     tagList.add(tag);
                                 }
                                 putTagList(tagList);
                                 tags = tagList;
                             }

                             return tags;
                         })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tagList -> mGankState.setTagList(tagList));
    }

    @SuppressLint("NewApi")
    @NonNull
    public void putTagList(@NonNull List<Tag> tagList) {
        mGankDb.putTagList(tagList);
    }

    @SuppressLint("NewApi")
    @NonNull
    public Disposable loadAndroidData(@NonNull int viewId, @NonNull int page) {
        return RxJavaInterop.toV2Flowable(mGankDb.getGankList(typeArray[0], page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankAndroid(viewId, page, ganks));
    }

    @SuppressLint("NewApi")
    @NonNull
    public Disposable loadIosData(@NonNull int viewId, @NonNull int page) {
        return RxJavaInterop.toV2Flowable(mGankDb.getGankList(typeArray[1], page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankIos(viewId, page, ganks));
    }

    @SuppressLint("NewApi")
    @NonNull
    public Disposable loadFrontData(@NonNull int viewId, @NonNull int page) {
        return RxJavaInterop.toV2Flowable(mGankDb.getGankList(typeArray[2], page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankFront(viewId, page, ganks));
    }

    @SuppressLint("NewApi")
    @NonNull
    public Disposable loadExpandData(@NonNull int viewId, @NonNull int page) {
        return RxJavaInterop.toV2Flowable(mGankDb.getGankList(typeArray[3], page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankExpand(viewId, page, ganks));
    }

    @SuppressLint("NewApi")
    @NonNull
    public Disposable loadWelfareData(@NonNull int viewId, @NonNull int page) {
        return RxJavaInterop.toV2Flowable(mGankDb.getGankList(typeArray[4], page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankWelfare(viewId, page, ganks));
    }

    @SuppressLint("NewApi")
    @NonNull
    public Disposable loadVideoData(@NonNull int viewId, @NonNull int page) {
        return RxJavaInterop.toV2Flowable(mGankDb.getGankList(typeArray[5], page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankVideo(viewId, page, ganks));
    }

    @SuppressLint("NewApi")
    @NonNull
    public Disposable getAndroidData(@NonNull int viewId, @NonNull int page) {
        return mGankIO.getAndroidData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Flowable::fromIterable)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .doOnSuccess(mGankDb::putGankList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankAndroid(viewId, page, ganks),
                        t -> {
                            mRxErrorProcessor.tryWithRxError(t,
                                rxError -> mGankState.notifyRxError(viewId, rxError));

                            if (page == 1) {
                                mGankState.notifyDbLoad(viewId, GankType.ANDROID);
                            }
                });
    }

    @SuppressLint("NewApi")
    @NonNull
    public Disposable getIosData(@NonNull int viewId, @NonNull int page) {
        return mGankIO.getIosData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Flowable::fromIterable)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .doOnSuccess(mGankDb::putGankList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankIos(viewId, page, ganks),
                        t -> {
                            mRxErrorProcessor.tryWithRxError(t,
                                    rxError -> mGankState.notifyRxError(viewId, rxError));

                            if (page == 1) {
                                mGankState.notifyDbLoad(viewId, GankType.IOS);
                            }
                });
    }

    @SuppressLint("NewApi")
    @NonNull
    public Disposable getWelfareData(@NonNull int viewId, @NonNull int page) {
        return mGankIO.getMMData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Flowable::fromIterable)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .doOnSuccess(mGankDb::putGankList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankWelfare(viewId, page, ganks),
                        t -> {
                            mRxErrorProcessor.tryWithRxError(t,
                                    rxError -> mGankState.notifyRxError(viewId, rxError));

                            if (page == 1) {
                                mGankState.notifyDbLoad(viewId, GankType.WELFARE);
                            }
                });
    }

    @SuppressLint("NewApi")
    @NonNull
    public Disposable getFrontData(@NonNull int viewId, @NonNull int page) {
        return mGankIO.getFrontData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Flowable::fromIterable)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .doOnSuccess(mGankDb::putGankList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankFront(viewId, page, ganks),
                        t -> {
                            mRxErrorProcessor.tryWithRxError(t,
                                    rxError -> mGankState.notifyRxError(viewId, rxError));

                            if (page == 1) {
                                mGankState.notifyDbLoad(viewId, GankType.FROANT);
                            }
                });
    }

    @SuppressLint("NewApi")
    @NonNull
    public Disposable getExpandData(@NonNull int viewId, @NonNull int page) {
        return mGankIO.getExpandData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Flowable::fromIterable)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .doOnSuccess(mGankDb::putGankList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankExpand(viewId, page, ganks),
                        t -> {
                            mRxErrorProcessor.tryWithRxError(t,
                                    rxError -> mGankState.notifyRxError(viewId, rxError));

                            if (page == 1) {
                                mGankState.notifyDbLoad(viewId, GankType.EXPAND);
                            }
                });
    }

    @SuppressLint("NewApi")
    @NonNull
    public Disposable getVideoData(@NonNull int viewId, @NonNull int page) {
        return mGankIO.getVideoData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Flowable::fromIterable)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .doOnSuccess(mGankDb::putGankList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankVideo(viewId, page, ganks),
                        t -> {
                            mRxErrorProcessor.tryWithRxError(t,
                                    rxError -> mGankState.notifyRxError(viewId, rxError));

                            if (page == 1) {
                                mGankState.notifyDbLoad(viewId, GankType.VIDEO);
                            }
                });
    }

    public Disposable getCollectData(@NonNull int viewId, @NonNull int page) {
        return RxJavaInterop.toV2Flowable(mGankDb.getCollectList(page))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(ganks -> mGankState.setGankCollect(viewId, page, ganks));
    }

    public Disposable collectGank(final Gank gank) {
        return Flowable.create(subscriber -> {
            subscriber.onNext(mGankDb.collectGank(gank));
            subscriber.onComplete();
        }, BackpressureStrategy.BUFFER)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(ret -> mGankState.notifyCollect(CollectType.INSERT));
    }

    public Disposable deleteCollect(final Gank gank) {
        return Flowable.create(subscriber -> {
            subscriber.onNext(mGankDb.deleteCollect(gank));
            subscriber.onComplete();
        }, BackpressureStrategy.BUFFER)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(ret -> mGankState.notifyCollect(CollectType.DELETE));
    }

    public Disposable existGank(@NonNull String id) {
        return RxJavaInterop.toV2Flowable(mGankDb.existCollect(id))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(gankCollect -> {mGankState.notifyCollect(CollectType.QUERY);});
    }

}
