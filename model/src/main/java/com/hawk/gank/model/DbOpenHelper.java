package com.hawk.gank.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.hawk.gank.model.gank.Gank;
import com.hawk.gank.model.gank.GankCollect;
import com.hawk.gank.model.gank.Tag;

import javax.inject.Inject;
import javax.inject.Singleton;

import static android.R.attr.version;

/**
 * Created by heyong on 2016/11/27.
 */
@Singleton
public class DbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_gank";
    private static final int VERSION = 1;

    @Inject
    public DbOpenHelper(@NonNull Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Tag.CREATE_TABLE);
        sqLiteDatabase.execSQL(Gank.CREATE_TABLE);
        sqLiteDatabase.execSQL(GankCollect.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
