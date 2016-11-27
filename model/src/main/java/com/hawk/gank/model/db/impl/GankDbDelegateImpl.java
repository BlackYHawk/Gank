package com.hawk.gank.model.db.impl;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.hawk.gank.model.db.GankDbDelegate;
import com.hawk.gank.model.gank.Gank;
import com.hawk.gank.model.gank.GankIO;
import com.hawk.lib.mvp.qualifiers.ActivityScope;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by heyong on 2016/11/27.
 */
@ActivityScope
public class GankDbDelegateImpl implements GankDbDelegate {
    private final BriteDatabase mBriteDb;

    @Inject
    public GankDbDelegateImpl(final BriteDatabase briteDatabase) {
        this.mBriteDb = briteDatabase;
    }

    @Override
    public void putGankList(List<Gank> gankList) {
        final BriteDatabase.Transaction transaction = mBriteDb.newTransaction();

        try {
            for (Gank gank : gankList) {
                mBriteDb.insert(Gank.TABLE_NAME, Gank.FACTORY.marshal(gank).asContentValues(),
                        SQLiteDatabase.CONFLICT_REPLACE);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }

    }

    @Override
    public Observable<List<Gank>> getGankList(@NonNull String type, @NonNull int page) {
        return mBriteDb.createQuery(Gank.TABLE_NAME, Gank.SELECT_PAGE, new String[]{type, GankIO.PAGE_SIZE+"", page+""})
                .mapToList(Gank.MAPPER::map);
    }
}
