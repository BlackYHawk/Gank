package com.hawk.gank.model.bean;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import org.threeten.bp.ZonedDateTime;

abstract class $AutoValue_Gank extends $$AutoValue_Gank {
  $AutoValue_Gank(String _id, String type, String url, List<String> images, ZonedDateTime createdAt, ZonedDateTime publishedAt, String description) {
    super(_id, type, url, images, createdAt, publishedAt, description);
  }

  public static final class GsonTypeAdapter extends TypeAdapter<Gank> {
    private final TypeAdapter<String> _idAdapter;
    private final TypeAdapter<String> typeAdapter;
    private final TypeAdapter<String> urlAdapter;
    private final TypeAdapter<List<String>> imagesAdapter;
    private final TypeAdapter<ZonedDateTime> createdAtAdapter;
    private final TypeAdapter<ZonedDateTime> publishedAtAdapter;
    private final TypeAdapter<String> descriptionAdapter;
    public GsonTypeAdapter(Gson gson) {
      this._idAdapter = gson.getAdapter(String.class);
      this.typeAdapter = gson.getAdapter(String.class);
      this.urlAdapter = gson.getAdapter(String.class);
      this.imagesAdapter = gson.getAdapter(new TypeToken<List<String>>(){});
      this.createdAtAdapter = gson.getAdapter(ZonedDateTime.class);
      this.publishedAtAdapter = gson.getAdapter(ZonedDateTime.class);
      this.descriptionAdapter = gson.getAdapter(String.class);
    }
    @Override
    public void write(JsonWriter jsonWriter, Gank object) throws IOException {
      jsonWriter.beginObject();
      jsonWriter.name("_id");
      _idAdapter.write(jsonWriter, object._id());
      jsonWriter.name("type");
      typeAdapter.write(jsonWriter, object.type());
      jsonWriter.name("url");
      urlAdapter.write(jsonWriter, object.url());
      if (object.images() != null) {
        jsonWriter.name("images");
        imagesAdapter.write(jsonWriter, object.images());
      }
      jsonWriter.name("createdAt");
      createdAtAdapter.write(jsonWriter, object.createdAt());
      jsonWriter.name("publishedAt");
      publishedAtAdapter.write(jsonWriter, object.publishedAt());
      jsonWriter.name("desc");
      descriptionAdapter.write(jsonWriter, object.description());
      jsonWriter.endObject();
    }
    @Override
    public Gank read(JsonReader jsonReader) throws IOException {
      jsonReader.beginObject();
      String _id = null;
      String type = null;
      String url = null;
      List<String> images = null;
      ZonedDateTime createdAt = null;
      ZonedDateTime publishedAt = null;
      String description = null;
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
          case "type": {
            type = typeAdapter.read(jsonReader);
            break;
          }
          case "url": {
            url = urlAdapter.read(jsonReader);
            break;
          }
          case "images": {
            images = imagesAdapter.read(jsonReader);
            break;
          }
          case "createdAt": {
            createdAt = createdAtAdapter.read(jsonReader);
            break;
          }
          case "publishedAt": {
            publishedAt = publishedAtAdapter.read(jsonReader);
            break;
          }
          case "desc": {
            description = descriptionAdapter.read(jsonReader);
            break;
          }
          default: {
            jsonReader.skipValue();
          }
        }
      }
      jsonReader.endObject();
      return new AutoValue_Gank(_id, type, url, images, createdAt, publishedAt, description);
    }
  }
}
