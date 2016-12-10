package com.hawk.gank.model.bean;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.Boolean;
import java.lang.Override;
import java.lang.String;

abstract class $AutoValue_Tag extends $$AutoValue_Tag {
  $AutoValue_Tag(String type, boolean valid) {
    super(type, valid);
  }

  public static final class GsonTypeAdapter extends TypeAdapter<Tag> {
    private final TypeAdapter<String> typeAdapter;
    private final TypeAdapter<Boolean> validAdapter;
    public GsonTypeAdapter(Gson gson) {
      this.typeAdapter = gson.getAdapter(String.class);
      this.validAdapter = gson.getAdapter(Boolean.class);
    }
    @Override
    public void write(JsonWriter jsonWriter, Tag object) throws IOException {
      jsonWriter.beginObject();
      jsonWriter.name("type");
      typeAdapter.write(jsonWriter, object.type());
      jsonWriter.name("valid");
      validAdapter.write(jsonWriter, object.valid());
      jsonWriter.endObject();
    }
    @Override
    public Tag read(JsonReader jsonReader) throws IOException {
      jsonReader.beginObject();
      String type = null;
      boolean valid = false;
      while (jsonReader.hasNext()) {
        String _name = jsonReader.nextName();
        if (jsonReader.peek() == JsonToken.NULL) {
          jsonReader.skipValue();
          continue;
        }
        switch (_name) {
          case "type": {
            type = typeAdapter.read(jsonReader);
            break;
          }
          case "valid": {
            valid = validAdapter.read(jsonReader);
            break;
          }
          default: {
            jsonReader.skipValue();
          }
        }
      }
      jsonReader.endObject();
      return new AutoValue_Tag(type, valid);
    }
  }
}
