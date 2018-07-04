package com.hawk.gank.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.squareup.sqldelight.ColumnAdapter;
import com.squareup.sqldelight.RowMapper;
import com.squareup.sqldelight.SqlDelightCompiledStatement;
import com.squareup.sqldelight.SqlDelightStatement;
import java.lang.Deprecated;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import org.threeten.bp.ZonedDateTime;

public interface GankModel {
  String TABLE_NAME = "Gank";

  String _ID = "_id";

  String DESCRIPTION = "description";

  String TYPE = "type";

  String URL = "url";

  String IMAGES = "images";

  String CREATEDAT = "createdAt";

  String PUBLISHEDAT = "publishedAt";

  String CREATE_TABLE = ""
      + "CREATE TABLE Gank (\n"
      + "    _id TEXT NOT NULL PRIMARY KEY,\n"
      + "    description TEXT NOT NULL,\n"
      + "    type TEXT NOT NULL,\n"
      + "    url TEXT NOT NULL,\n"
      + "    images TEXT,\n"
      + "    createdAt TEXT NOT NULL,\n"
      + "    publishedAt TEXT NOT NULL\n"
      + "  )";

  String CLEAR = ""
      + "DELETE FROM Gank WHERE 1";

  @NonNull
  String _id();

  @NonNull
  String description();

  @NonNull
  String type();

  @NonNull
  String url();

  @Nullable
  List<String> images();

  @NonNull
  ZonedDateTime createdAt();

  @NonNull
  ZonedDateTime publishedAt();

  interface Creator<T extends GankModel> {
    T create(@NonNull String _id, @NonNull String description, @NonNull String type, @NonNull String url, @Nullable List<String> images, @NonNull ZonedDateTime createdAt, @NonNull ZonedDateTime publishedAt);
  }

  final class Mapper<T extends GankModel> implements RowMapper<T> {
    private final Factory<T> gankModelFactory;

    public Mapper(Factory<T> gankModelFactory) {
      this.gankModelFactory = gankModelFactory;
    }

    @Override
    public T map(@NonNull Cursor cursor) {
      return gankModelFactory.creator.create(
          cursor.getString(0),
          cursor.getString(1),
          cursor.getString(2),
          cursor.getString(3),
          cursor.isNull(4) ? null : gankModelFactory.imagesAdapter.decode(cursor.getString(4)),
          gankModelFactory.createdAtAdapter.decode(cursor.getString(5)),
          gankModelFactory.publishedAtAdapter.decode(cursor.getString(6))
      );
    }
  }

  final class Marshal {
    protected final ContentValues contentValues = new ContentValues();

    private final ColumnAdapter<List<String>, String> imagesAdapter;

    private final ColumnAdapter<ZonedDateTime, String> createdAtAdapter;

    private final ColumnAdapter<ZonedDateTime, String> publishedAtAdapter;

    Marshal(@Nullable GankModel copy, ColumnAdapter<List<String>, String> imagesAdapter, ColumnAdapter<ZonedDateTime, String> createdAtAdapter, ColumnAdapter<ZonedDateTime, String> publishedAtAdapter) {
      this.imagesAdapter = imagesAdapter;
      this.createdAtAdapter = createdAtAdapter;
      this.publishedAtAdapter = publishedAtAdapter;
      if (copy != null) {
        this._id(copy._id());
        this.description(copy.description());
        this.type(copy.type());
        this.url(copy.url());
        this.images(copy.images());
        this.createdAt(copy.createdAt());
        this.publishedAt(copy.publishedAt());
      }
    }

    public ContentValues asContentValues() {
      return contentValues;
    }

    public Marshal _id(String _id) {
      contentValues.put("_id", _id);
      return this;
    }

    public Marshal description(String description) {
      contentValues.put("description", description);
      return this;
    }

    public Marshal type(String type) {
      contentValues.put("type", type);
      return this;
    }

    public Marshal url(String url) {
      contentValues.put("url", url);
      return this;
    }

    public Marshal images(@Nullable List<String> images) {
      if (images != null) {
        contentValues.put("images", imagesAdapter.encode(images));
      } else {
        contentValues.putNull("images");
      }
      return this;
    }

    public Marshal createdAt(@NonNull ZonedDateTime createdAt) {
      contentValues.put("createdAt", createdAtAdapter.encode(createdAt));
      return this;
    }

    public Marshal publishedAt(@NonNull ZonedDateTime publishedAt) {
      contentValues.put("publishedAt", publishedAtAdapter.encode(publishedAt));
      return this;
    }
  }

  final class Factory<T extends GankModel> {
    public final Creator<T> creator;

    public final ColumnAdapter<List<String>, String> imagesAdapter;

    public final ColumnAdapter<ZonedDateTime, String> createdAtAdapter;

    public final ColumnAdapter<ZonedDateTime, String> publishedAtAdapter;

    public Factory(Creator<T> creator, ColumnAdapter<List<String>, String> imagesAdapter, ColumnAdapter<ZonedDateTime, String> createdAtAdapter, ColumnAdapter<ZonedDateTime, String> publishedAtAdapter) {
      this.creator = creator;
      this.imagesAdapter = imagesAdapter;
      this.createdAtAdapter = createdAtAdapter;
      this.publishedAtAdapter = publishedAtAdapter;
    }

    /**
     * @deprecated Use compiled statements (https://github.com/square/sqldelight#compiled-statements)
     */
    @Deprecated
    public Marshal marshal() {
      return new Marshal(null, imagesAdapter, createdAtAdapter, publishedAtAdapter);
    }

    /**
     * @deprecated Use compiled statements (https://github.com/square/sqldelight#compiled-statements)
     */
    @Deprecated
    public Marshal marshal(GankModel copy) {
      return new Marshal(copy, imagesAdapter, createdAtAdapter, publishedAtAdapter);
    }

    public SqlDelightStatement select_page(@NonNull String type, long arg2, long arg3) {
      List<String> args = new ArrayList<String>();
      int currentIndex = 1;
      StringBuilder query = new StringBuilder();
      query.append("SELECT * FROM Gank where type = ");
      query.append('?').append(currentIndex++);
      args.add(type);
      query.append(" order by publishedAt desc limit ");
      query.append(arg2);
      query.append(" offset ");
      query.append(arg3);
      return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("Gank"));
    }

    public SqlDelightStatement select_collect(long arg1, long arg2) {
      List<String> args = new ArrayList<String>();
      int currentIndex = 1;
      StringBuilder query = new StringBuilder();
      query.append("SELECT Gank.* FROM Gank inner join Gank_Collect on Gank._id=Gank_Collect._id limit ");
      query.append(arg1);
      query.append(" offset ");
      query.append(arg2);
      return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>unmodifiableSet(new LinkedHashSet<String>(Arrays.asList("Gank","Gank_Collect"))));
    }

    public Mapper<T> select_pageMapper() {
      return new Mapper<T>(this);
    }

    public Mapper<T> select_collectMapper() {
      return new Mapper<T>(this);
    }
  }

  final class Clear extends SqlDelightCompiledStatement.Delete {
    public Clear(SQLiteDatabase database) {
      super("Gank", database.compileStatement(""
              + "DELETE FROM Gank WHERE 1"));
    }
  }
}
