package com.hawk.gank.model.gank;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.hawk.lib.mvp.qualifiers.ActivityScope;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by heyong on 2016/11/7.
 */
@ActivityScope
public class GankRepo {
    private final GankIO mGankIO;

    @Inject
    GankRepo(final GankIO mGankIO) {
        this.mGankIO = mGankIO;
    }

    @SuppressLint("NewApi")
    @NonNull
    public Observable<List<Gank>> getMMData(@NonNull int page) {
        return mGankIO.getMMData(page)
                .map(GankResult::results);
    }

}
