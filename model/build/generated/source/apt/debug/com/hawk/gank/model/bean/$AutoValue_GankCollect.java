package com.hawk.gank.model.bean;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.Override;
import java.lang.String;
import org.threeten.bp.ZonedDateTime;

abstract class $AutoValue_GankCollect extends $$AutoValue_GankCollect {
  $AutoValue_GankCollect(String _id, ZonedDateTime collectedAt) {
    super(_id, collectedAt);
  }

  public static final class GsonTypeAdapter extends TypeAdapter<GankCollect> {
    private final TypeAdapter<String> _idAdapter;
    private final TypeAdapter<ZonedDateTime> collectedAtAdapter;
    public GsonTypeAdapter(Gson gson) {
      this._idAdapter = gson.getAdapter(String.class);
      this.collectedAtAdapter = gson.getAdapter(ZonedDateTime.class);
    }
    @Override
    public void write(JsonWriter jsonWriter, GankCollect object) throws IOException {
      jsonWriter.beginObject();
      jsonWriter.name("_id");
      _idAdapter.write(jsonWriter, object._id());
      jsonWriter.name("collectedAt");
      collectedAtAdapter.write(jsonWriter, object.collectedAt());
      jsonWriter.endObject();
    }
    @Override
    public GankCollect read(JsonReader jsonReader) throws IOException {
      jsonReader.beginObject();
      String _id = null;
      ZonedDateTime collectedAt = null;
      while (jsonReader.hasNext()) {
        String _name = jsonReader.nextName();
        if (jsonReader.peek() == JsonToken.NULL) {
          jsonReader.skipValue();
          continue;
        }
        switch (_name) {
          case "_id": {
            _id = _idAdapter.read(jsonReader);
            break;
          }
          case "collectedAt": {
            collectedAt = collectedAtAdapter.read(jsonReader);
            break;
          }
          default: {
            jsonReader.skipValue();
          }
        }
      }
      jsonReader.endObject();
      return new AutoValue_GankCollect(_id, collectedAt);
    }
  }
}
