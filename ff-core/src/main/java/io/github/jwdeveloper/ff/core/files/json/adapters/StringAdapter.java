package io.github.jwdeveloper.ff.core.files.json.adapters;

import com.google.gson.*;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;

import java.lang.reflect.Type;

public class StringAdapter implements JsonSerializer<String>, JsonDeserializer<String> {

    @Override
    public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }

    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return json.getAsString();
        } catch (NumberFormatException e) {
            throw new JsonParseException(e);
        }
    }
}