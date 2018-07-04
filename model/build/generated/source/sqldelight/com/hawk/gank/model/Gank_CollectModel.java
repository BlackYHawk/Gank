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
import java.util.Collections;
import java.util.List;
import org.threeten.bp.ZonedDateTime;

public interface Gank_CollectModel {
  String TABLE_NAME = "Gank_Collect";

  String _ID = "_id";

  String COLLECTEDAT = "collectedAt";

  String CREATE_TABLE = ""
      + "CREATE TABLE Gank_Collect (\n"
      + "    _id TEXT NOT NULL PRIMARY KEY,\n"
      + "    collectedAt TEXT NOT NULL,\n"
      + "    CONSTRAINT id_Fk FOREIGN KEY(_id) REFERENCES Gank(_id)\n"
      + ")";

  String CLEAR = ""
      + "DELETE FROM Gank_Collect WHERE 1";

  @NonNull
  String _id();

  @NonNull
  ZonedDateTime collectedAt();

  interface Creator<T extends Gank_CollectModel> {
    T create(@NonNull String _id, @NonNull ZonedDateTime collectedAt);
  }

  final class Mapper<T extends Gank_CollectModel> implements RowMapper<T> {
    private final Factory<T> gank_CollectModelFactory;

    public Mapper(Factory<T> gank_CollectModelFactory) {
      this.gank_CollectModelFactory = gank_CollectModelFactory;
    }

    @Override
    public T map(@NonNull Cursor cursor) {
      return gank_CollectModelFactory.creator.create(
          cursor.getString(0),
          gank_CollectModelFactory.collectedAtAdapter.decode(cursor.getString(1))
      );
    }
  }

  final class Marshal {
    protected final ContentValues contentValues = new ContentValues();

    private final ColumnAdapter<ZonedDateTime, String> collectedAtAdapter;

    Marshal(@Nullable Gank_CollectModel copy, ColumnAdapter<ZonedDateTime, String> collectedAtAdapter) {
      this.collectedAtAdapter = collectedAtAdapter;
      if (copy != null) {
        this._id(copy._id());
        this.collectedAt(copy.collectedAt());
      }
    }

    public ContentValues asContentValues() {
      return contentValues;
    }

    public Marshal _id(String _id) {
      contentValues.put("_id", _id);
      return this;
    }

    public Marshal collectedAt(@NonNull ZonedDateTime collectedAt) {
      contentValues.put("collectedAt", collectedAtAdapter.encode(collectedAt));
      return this;
    }
  }

  final class Factory<T extends Gank_CollectModel> {
    public final Creator<T> creator;

    public final ColumnAdapter<ZonedDateTime, String> collectedAtAdapter;

    public Factory(Creator<T> creator, ColumnAdapter<ZonedDateTime, String> collectedAtAdapter) {
      this.creator = creator;
      this.collectedAtAdapter = collectedAtAdapter;
    }

    /**
     * @deprecated Use compiled statements (https://github.com/square/sqldelight#compiled-statements)
     */
    @Deprecated
    public Marshal marshal() {
      return new Marshal(null, collectedAtAdapter);
    }

    /**
     * @deprecated Use compiled statements (https://github.com/square/sqldelight#compiled-statements)
     */
    @Deprecated
    public Marshal marshal(Gank_CollectModel copy) {
      return new Marshal(copy, collectedAtAdapter);
    }

    public SqlDelightStatement select_gank(@NonNull String _id) {
      List<String> args = new ArrayList<String>();
      int currentIndex = 1;
      StringBuilder query = new StringBuilder();
      query.append("SELECT * FROM Gank_Collect where _id = ");
      query.append('?').append(currentIndex++);
      args.add(_id);
      return new SqlDelightStatement(query.toString(), args.toArray(new String[args.size()]), Collections.<String>singleton("Gank_Collect"));
    }

    public Mapper<T> select_gankMapper() {
      return new Mapper<T>(this);
    }
  }

  final class Clear extends SqlDelightCompiledStatement.Delete {
    public Clear(SQLiteDatabase database) {
      super("Gank_Collect", database.compileStatement(""
              + "DELETE FROM Gank_Collect WHERE 1"));
    }
  }
}
