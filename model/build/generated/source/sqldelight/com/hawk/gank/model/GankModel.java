package com.hawk.gank.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.squareup.sqldelight.ColumnAdapter;
import com.squareup.sqldelight.RowMapper;
import java.lang.Deprecated;
import java.lang.Override;
import java.lang.String;
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
      + "    _id TEXT PRIMARY KEY,\n"
      + "    description TEXT NOT NULL,\n"
      + "    type TEXT NOT NULL,\n"
      + "    url TEXT NOT NULL,\n"
      + "    images TEXT NOT NULL,\n"
      + "    createdAt TEXT NOT NULL,\n"
      + "    publishedAt TEXT NOT NULL\n"
      + "  )";

  String DELETE_ALL = ""
      + "DELETE FROM Gank WHERE 1";

  String SELECT_ALL = ""
      + "SELECT * FROM Gank";

  @Nullable
  String _id();

  @NonNull
  String description();

  @NonNull
  String type();

  @NonNull
  String url();

  @NonNull
  String images();

  @NonNull
  ZonedDateTime createdAt();

  @NonNull
  ZonedDateTime publishedAt();

  interface Creator<T extends GankModel> {
    T create(@Nullable String _id, @NonNull String description, @NonNull String type, @NonNull String url, @NonNull String images, @NonNull ZonedDateTime createdAt, @NonNull ZonedDateTime publishedAt);
  }

  final class Mapper<T extends GankModel> implements RowMapper<T> {
    private final Factory<T> gankModelFactory;

    public Mapper(Factory<T> gankModelFactory) {
      this.gankModelFactory = gankModelFactory;
    }

    @Override
    public T map(@NonNull Cursor cursor) {
      return gankModelFactory.creator.create(
          cursor.isNull(0) ? null : cursor.getString(0),
          cursor.getString(1),
          cursor.getString(2),
          cursor.getString(3),
          cursor.getString(4),
          gankModelFactory.createdAtAdapter.decode(cursor.getString(5)),
          gankModelFactory.publishedAtAdapter.decode(cursor.getString(6))
      );
    }
  }

  final class Marshal {
    protected final ContentValues contentValues = new ContentValues();

    private final ColumnAdapter<ZonedDateTime, String> createdAtAdapter;

    private final ColumnAdapter<ZonedDateTime, String> publishedAtAdapter;

    Marshal(@Nullable GankModel copy, ColumnAdapter<ZonedDateTime, String> createdAtAdapter, ColumnAdapter<ZonedDateTime, String> publishedAtAdapter) {
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

    public Marshal images(String images) {
      contentValues.put("images", images);
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

    public final ColumnAdapter<ZonedDateTime, String> createdAtAdapter;

    public final ColumnAdapter<ZonedDateTime, String> publishedAtAdapter;

    public Factory(Creator<T> creator, ColumnAdapter<ZonedDateTime, String> createdAtAdapter, ColumnAdapter<ZonedDateTime, String> publishedAtAdapter) {
      this.creator = creator;
      this.createdAtAdapter = createdAtAdapter;
      this.publishedAtAdapter = publishedAtAdapter;
    }

    /**
     * @deprecated Use compiled statements (https://github.com/square/sqldelight#compiled-statements)
     */
    @Deprecated
    public Marshal marshal() {
      return new Marshal(null, createdAtAdapter, publishedAtAdapter);
    }

    /**
     * @deprecated Use compiled statements (https://github.com/square/sqldelight#compiled-statements)
     */
    @Deprecated
    public Marshal marshal(GankModel copy) {
      return new Marshal(copy, createdAtAdapter, publishedAtAdapter);
    }

    public Mapper<T> select_allMapper() {
      return new Mapper<T>(this);
    }
  }
}
