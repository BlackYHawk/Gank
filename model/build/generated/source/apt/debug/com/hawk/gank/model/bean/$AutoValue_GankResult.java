package com.hawk.gank.model.bean;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.Boolean;
import java.lang.Override;
import java.lang.String;
import java.util.Collections;
import java.util.List;

abstract class $AutoValue_GankResult extends $$AutoValue_GankResult {
  $AutoValue_GankResult(boolean error, List<Gank> results) {
    super(error, results);
  }

  public static final class GsonTypeAdapter extends TypeAdapter<GankResult> {
    private final TypeAdapter<Boolean> errorAdapter;
    private final TypeAdapter<List<Gank>> resultsAdapter;
    public GsonTypeAdapter(Gson gson) {
      this.errorAdapter = gson.getAdapter(Boolean.class);
      this.resultsAdapter = gson.getAdapter(new TypeToken<List<Gank>>(){});
    }
    @Override
    public void write(JsonWriter jsonWriter, GankResult object) throws IOException {
      jsonWriter.beginObject();
      jsonWriter.name("error");
      errorAdapter.write(jsonWriter, object.error());
      jsonWriter.name("results");
      resultsAdapter.write(jsonWriter, object.results());
      jsonWriter.endObject();
    }
    @Override
    public GankResult read(JsonReader jsonReader) throws IOException {
      jsonReader.beginObject();
      boolean error = false;
      List<Gank> results = Collections.emptyList();
      while (jsonReader.hasNext()) {
        String _name = jsonReader.nextName();
        if (jsonReader.peek() == JsonToken.NULL) {
          jsonReader.skipValue();
          continue;
        }
        switch (_name) {
          case "error": {
            error = errorAdapter.read(jsonReader);
            break;
          }
          case "results": {
            results = resultsAdapter.read(jsonReader);
            break;
          }
          default: {
            jsonReader.skipValue();
          }
        }
      }
      jsonReader.endObject();
      return new AutoValue_GankResult(error, results);
    }
  }
}
