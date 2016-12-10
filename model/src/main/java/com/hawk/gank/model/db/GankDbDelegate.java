package com.hawk.gank.model.db;

import android.support.annotation.NonNull;

import com.hawk.gank.model.bean.Gank;
import com.hawk.gank.model.bean.Tag;

import java.util.List;

import rx.Observable;

/**
 * Created by heyong on 2016/11/27.
 */

public interface GankDbDelegate {

    long addTag(Tag tag);

    int updateTag(Tag tag);

    void putTagList(List<Tag> tagList);

    Observable<List<Tag>> getTagList();

    void putGankList(List<Gank> gankList);

    Observable<List<Gank>> getGankList(@NonNull String type, @NonNull int page);

}
