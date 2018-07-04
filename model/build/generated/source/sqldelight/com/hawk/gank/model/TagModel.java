package com.hawk.gank.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.squareup.sqldelight.RowMapper;
import com.squareup.sqldelight.SqlDelightCompiledStatement;
import com.squareup.sqldelight.SqlDelightStatement;
import java.lang.Deprecated;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface TagModel {
  String TABLE_NAME = "Tag";

  String TYPE = "type";

  String VALID = "valid";

  String CREATE_TABLE = ""
      + "CREATE TABLE Tag (\n"
      + "    type TEXT NOT NULL PRIMARY KEY,\n"
      + "    valid INTEGER DEFAULT 0 NOT NULL\n"
      + "  )";

  @NonNull
  String type();

  boolean valid();

  interface Creator<T extends TagModel> {
    T create(@NonNull String type, boolean valid);
  }

  final class Mapper<T extends TagModel> implements RowMapper<T> {
    private final Factory<T> tagModelFactory;

    public Mapper(Factory<T> tagModelFactory) {
      this.tagModelFactory = tagModelFactory;
    }

    @Override
    public T map(@NonNull Cursor cursor) {
      return tagModelFactory.creator.create(
          cursor.getString(0),
          cursor.getInt(1) == 1
      );
    }
  }

  final class Marshal {
    protected final ContentValues contentValues = new ContentValues();

    Marshal(@Nullable TagModel copy) {
      if (copy != null) {
        this.type(copy.type());
        this.valid(copy.valid());
      }
    }

    public ContentValues asContentValues() {
      return contentValues;
    }

    public Marshal type(String type) {
      contentValues.put("type", type);
      return this;
    }

    public Marshal valid(boolean valid) {
      contentValues.put("valid", valid ? 1 : 0);
      return this;
    }
  }

  final class Factory<T extends TagModel> {
    public final Creator<T> creator;

    public Factory(Creator<T> creator) {
      this.creator = creator;
    }

    /**
     * @deprecated Use compiled statements (https://github.com/square/sqldelight#compiled-statements)
     */
    @Deprecated
    public Marshal marshal() {
      return new Marshal(null);
    }

    /**
     * @deprecated Use compiled statements (https://github.com/square/sqldelight#compiled-statements)
     */
    @Deprecated
    public Marshal marshal(TagModel copy) {
      return new Marshal(copy);
    }

    public SqlDelightStatement select_all() {
      return new SqlDelightStatement(""
          + "SELECT * FROM Tag",
          new String[0], Collections.<String>singleton("Tag"));
    }

    /**
     * @deprecated Use {@link Update_row}
     */
    @Deprecated
    public SqlDelightStatement update_row(boolean valid, @NonNull String type) {
      List<String> args = new ArrayList<String>();
      int currentIndex = 1;
      StringBuilder query = new StringBuilder();
      query.append("UPDATE Tag SET valid = ");
      query.append(valid ? 1 : 0);
      query.append(" WHERE type = ");
      query.append('?').append(currentIndex++);
      args.add(type);
      return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("Tag"));
    }

    public Mapper<T> select_allMapper() {
      return new Mapper<T>(this);
    }
  }

  final class Update_row extends SqlDelightCompiledStatement.Update {
    public Update_row(SQLiteDatabase database) {
      super("Tag", database.compileStatement(""
              + "UPDATE Tag SET valid = ? WHERE type = ?"));
    }

    public void bind(boolean valid, @NonNull String type) {
      program.bindLong(1, valid ? 1 : 0);
      program.bindString(2, type);
    }
  }
}
