package com.hawk.gank.model.db.impl;

import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.hawk.gank.model.bean.Gank;
import com.hawk.gank.model.bean.GankCollect;
import com.hawk.gank.model.bean.Tag;
import com.hawk.gank.model.db.GankDbDelegate;
import com.hawk.gank.model.http.GankIO;
import com.hawk.lib.mvp.qualifiers.ActivityScope;
import com.squareup.sqlbrite.BriteDatabase;

import org.threeten.bp.ZonedDateTime;

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
    public long addTag(@NonNull Tag tag) {
        final BriteDatabase.Transaction transaction = mBriteDb.newTransaction();

        try {
            long rowId = mBriteDb.insert(Tag.TABLE_NAME, Tag.FACTORY.marshal(tag).asContentValues(),
                    SQLiteDatabase.CONFLICT_REPLACE);
            transaction.markSuccessful();

            return rowId;
        } finally {
            transaction.end();
        }
    }

    @Override
    public int updateTag(@NonNull Tag tag) {
        final BriteDatabase.Transaction transaction = mBriteDb.newTransaction();

        try {
            int count = mBriteDb.update(Tag.TABLE_NAME, Tag.FACTORY.marshal(tag).asContentValues(),
                    Tag.TYPE + "=?", tag.type());
            transaction.markSuccessful();

            return count;
        } finally {
            transaction.end();
        }
    }

    @Override
    public void putTagList(@NonNull List<Tag> typeList) {
        final BriteDatabase.Transaction transaction = mBriteDb.newTransaction();

        try {
            for (Tag tag : typeList) {
                mBriteDb.insert(Tag.TABLE_NAME, Tag.FACTORY.marshal(tag).asContentValues(),
                        SQLiteDatabase.CONFLICT_REPLACE);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }

    @Override
    public Observable<List<Tag>> getTagList() {
        return mBriteDb.createQuery(Tag.TABLE_NAME, Tag.SELECT_ALL)
                .mapToList(Tag.MAPPER::map);
    }

    @Override
    public void putGankList(@NonNull List<Gank> gankList) {
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

    @Override
    public Observable<List<Gank>> getCollectList(@NonNull int page) {
        return mBriteDb.createQuery(Gank.TABLE_NAME, Gank.SELECT_COLLECT, new String[]{GankIO.PAGE_SIZE+"", page+""})
                .mapToList(Gank.MAPPER::map);
    }

    @Override
    public long collectGank(@NonNull Gank gank) {
        final BriteDatabase.Transaction transaction = mBriteDb.newTransaction();

        GankCollect collect = GankCollect.builder()._id(gank._id()).collectedAt(ZonedDateTime.now()).build();
        try {
            long rowId = mBriteDb.insert(GankCollect.TABLE_NAME, GankCollect.FACTORY.marshal(collect).asContentValues(),
                    SQLiteDatabase.CONFLICT_REPLACE);
            transaction.markSuccessful();

            return rowId;
        } finally {
            transaction.end();
        }
    }

    @Override
    public int deleteCollect(@NonNull Gank gank) {
        final BriteDatabase.Transaction transaction = mBriteDb.newTransaction();

        try {
            int count = mBriteDb.delete(GankCollect.TABLE_NAME, GankCollect._ID + "=?", gank._id());
            transaction.markSuccessful();

            return count;
        } finally {
            transaction.end();
        }
    }

    @Override
    public Observable<GankCollect> existCollect(@NonNull String id) {
        return mBriteDb.createQuery(GankCollect.TABLE_NAME, GankCollect.SELECT_GANK, new String[]{id})
                .mapToOne(GankCollect.MAPPER::map);
    }
}
