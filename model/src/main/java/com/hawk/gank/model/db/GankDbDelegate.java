package com.hawk.gank.model.db;

import android.support.annotation.NonNull;

import com.hawk.gank.model.bean.Gank;
import com.hawk.gank.model.bean.GankCollect;
import com.hawk.gank.model.bean.Tag;

import java.util.List;

import rx.Observable;

/**
 * Created by heyong on 2016/11/27.
 */

public interface GankDbDelegate {

    long addTag(@NonNull Tag tag);

    int updateTag(@NonNull Tag tag);

    void putTagList(@NonNull List<Tag> tagList);

    Observable<List<Tag>> getTagList();

    void putGankList(@NonNull List<Gank> gankList);

    Observable<List<Gank>> getGankList(@NonNull String type, @NonNull int page);

    Observable<List<Gank>> getCollectList(@NonNull int page);

    long collectGank(@NonNull Gank gank);

    int deleteCollect(@NonNull Gank gank);

    Observable<GankCollect> existCollect(@NonNull String id);

}
