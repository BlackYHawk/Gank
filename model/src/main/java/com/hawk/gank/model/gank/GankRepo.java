package com.hawk.gank.model.gank;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.hawk.gank.model.error.RxErrorProcessor;
import com.hawk.gank.model.state.GankState;
import com.hawk.lib.mvp.qualifiers.ActivityScope;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by heyong on 2016/11/7.
 */
@ActivityScope
public class GankRepo {
    private final GankIO mGankIO;
    private final GankState mGankState;
    private final RxErrorProcessor mRxErrorProcessor;

    @Inject
    GankRepo(final GankIO gankIO, final GankState gankState, final RxErrorProcessor rxErrorProcessor) {
        this.mGankIO = gankIO;
        this.mGankState = gankState;
        this.mRxErrorProcessor = rxErrorProcessor;
    }

    @SuppressLint("NewApi")
    @NonNull
    public Subscription getAndroidData(@NonNull int viewId, @NonNull int page) {
        return mGankIO.getAndroidData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Observable::from)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankAndroid(viewId, page, ganks),
                        t -> mRxErrorProcessor.tryWithRxError(t,
                                rxError -> mGankState.notifyRxError(viewId, rxError)));
    }

    @SuppressLint("NewApi")
    @NonNull
    public Subscription getIosData(@NonNull int viewId, @NonNull int page) {
        return mGankIO.getIosData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Observable::from)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankIos(viewId, page, ganks),
                        t -> mRxErrorProcessor.tryWithRxError(t,
                                rxError -> mGankState.notifyRxError(viewId, rxError)));
    }

    @SuppressLint("NewApi")
    @NonNull
    public Subscription getMMData(@NonNull int viewId, @NonNull int page) {
        return mGankIO.getMMData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Observable::from)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankWelfare(viewId, page, ganks),
                        t -> mRxErrorProcessor.tryWithRxError(t,
                                rxError -> mGankState.notifyRxError(viewId, rxError)));
    }

    @SuppressLint("NewApi")
    @NonNull
    public Subscription getFrontData(@NonNull int viewId, @NonNull int page) {
        return mGankIO.getFrontData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Observable::from)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankFront(viewId, page, ganks),
                        t -> mRxErrorProcessor.tryWithRxError(t,
                                rxError -> mGankState.notifyRxError(viewId, rxError)));
    }

    @SuppressLint("NewApi")
    @NonNull
    public Subscription getExpandData(@NonNull int viewId, @NonNull int page) {
        return mGankIO.getExpandData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Observable::from)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankExpand(viewId, page, ganks),
                        t -> mRxErrorProcessor.tryWithRxError(t,
                                rxError -> mGankState.notifyRxError(viewId, rxError)));
    }

    @SuppressLint("NewApi")
    @NonNull
    public Subscription getVideoData(@NonNull int viewId, @NonNull int page) {
        return mGankIO.getVideoData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Observable::from)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankVideo(viewId, page, ganks),
                        t -> mRxErrorProcessor.tryWithRxError(t,
                                rxError -> mGankState.notifyRxError(viewId, rxError)));
    }

}
