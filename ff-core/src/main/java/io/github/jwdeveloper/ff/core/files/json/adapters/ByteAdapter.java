package io.github.jwdeveloper.ff.core.files.json.adapters;


import com.google.gson.*;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;

import java.lang.reflect.Type;

public class ByteAdapter implements JsonSerializer<Byte>, JsonDeserializer<Byte> {

    @Override
    public JsonElement serialize(Byte src, Type typeOfSrc, JsonSerializationContext context) {

        return new JsonPrimitive(src);
    }

    @Override
    public Byte deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {

            return json.getAsByte();
        } catch (NumberFormatException e) {
            throw new JsonParseException(e);
        }
    }
}