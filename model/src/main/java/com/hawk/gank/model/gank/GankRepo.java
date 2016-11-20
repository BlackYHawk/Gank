package com.hawk.gank.model.gank;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

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

    @Inject
    GankRepo(final GankIO gankIO, final GankState gankState) {
        this.mGankIO = gankIO;
        this.mGankState = gankState;
    }

    @SuppressLint("NewApi")
    @NonNull
    public Subscription getAndroidData(@NonNull int page) {
        return mGankIO.getAndroidData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Observable::from)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(ganks -> mGankState.setGankAndroid(page, ganks))
                .subscribe();
    }

    @SuppressLint("NewApi")
    @NonNull
    public Subscription getIosData(@NonNull int page) {
        return mGankIO.getIosData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Observable::from)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(ganks -> mGankState.setGankIos(page, ganks))
                .subscribe();
    }

    @SuppressLint("NewApi")
    @NonNull
    public Subscription getMMData(@NonNull int page) {
        return mGankIO.getMMData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Observable::from)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(ganks -> mGankState.setGankWelfare(page, ganks))
                .subscribe();
    }

}
