package com.hawk.gank.model.db;

import android.support.annotation.NonNull;

import com.hawk.gank.model.gank.Gank;

import java.util.List;

import rx.Observable;

/**
 * Created by heyong on 2016/11/27.
 */

public interface GankDbDelegate {

    void putGankList(List<Gank> gankList);

    Observable<List<Gank>> getGankList(@NonNull String type, @NonNull int page);

}
