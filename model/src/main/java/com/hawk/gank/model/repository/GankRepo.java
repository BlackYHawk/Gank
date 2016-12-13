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
import com.hawk.gank.model.state.GankState;
import com.hawk.lib.base.util.ObjectUtil;
import com.hawk.lib.mvp.qualifiers.ActivityScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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

    public Subscription updateTag(final Tag tag) {
        return Observable.create(subscriber -> {
                subscriber.onNext(mGankDb.updateTag(tag));
                subscriber.onCompleted();
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(ret -> mGankState.updateTag(tag));
    }

    @SuppressLint("NewApi")
    @NonNull
    public Subscription getTagList() {
        return mGankDb.getTagList()
                .subscribeOn(Schedulers.io())
                .map(new Func1<List<Tag>, List<Tag>>() {
                         @Override
                         public List<Tag> call(List<Tag> tags) {
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
                         }})
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
    public Subscription getAndroidData(@NonNull int viewId, @NonNull int page) {
        return mGankIO.getAndroidData(page)
                .subscribeOn(Schedulers.io())
                .map(GankResult::results)
                .flatMap(Observable::from)
                .toSortedList((gankData1, gankData2) ->
                        gankData2.publishedAt().compareTo(gankData1.publishedAt()))
                .doOnNext(mGankDb::putGankList)
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
                .doOnNext(mGankDb::putGankList)
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
                .doOnNext(mGankDb::putGankList)
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
                .doOnNext(mGankDb::putGankList)
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
                .doOnNext(mGankDb::putGankList)
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
                .doOnNext(mGankDb::putGankList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ganks -> mGankState.setGankVideo(viewId, page, ganks),
                        t -> mRxErrorProcessor.tryWithRxError(t,
                                rxError -> mGankState.notifyRxError(viewId, rxError)));
    }

    public Subscription getCollectData(@NonNull int viewId, @NonNull int page) {
        return mGankDb.getCollectList(page)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(ganks -> mGankState.setGankCollect(viewId, page, ganks));
    }

    public Subscription collectGank(final Gank gank) {
        return Observable.create(subscriber -> {
            subscriber.onNext(mGankDb.collectGank(gank));
            subscriber.onCompleted();
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(ret -> mGankState.notifyCollect(CollectType.INSERT));
    }

    public Subscription deleteCollect(final Gank gank) {
        return Observable.create(subscriber -> {
            subscriber.onNext(mGankDb.deleteCollect(gank));
            subscriber.onCompleted();
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(ret -> mGankState.notifyCollect(CollectType.DELETE));
    }

    public Subscription existGank(@NonNull String id) {
        return mGankDb.existCollect(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(gankCollect -> {mGankState.notifyCollect(CollectType.QUERY);});
    }

}
