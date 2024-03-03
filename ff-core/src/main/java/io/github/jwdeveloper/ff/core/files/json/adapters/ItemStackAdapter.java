package io.github.jwdeveloper.ff.core.files.json.adapters;


import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import io.github.jwdeveloper.ff.core.files.json.JsonUtility;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.items.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.lang.reflect.Type;
import java.util.Map;

public class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {


    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        var value = jsonElement.getAsString();
        if (value.equals("#air#"))
        {
            return null;
        }
        try {
            FluentLogger.LOGGER.success("Trying to deserialize");
            var item = ItemStackUtils.deserializeItemStack(value.getBytes());
            FluentLogger.LOGGER.success("WE HAVE ITEM!", item);
            return item;
        } catch (Exception e) {
            e.printStackTrace();
            FluentLogger.LOGGER.info(e,"ERROR: " + value);

            return null;
        }
    }

    @Override
    public JsonElement serialize(ItemStack itemStack, Type type, JsonSerializationContext context) {

        FluentLogger.LOGGER.success("AAAAAA");
        if(itemStack.getType().isAir())
        {
            return new JsonPrimitive("#air#");
        }

        var res = ItemStackUtils.serializeItemStack(itemStack);
        return new JsonPrimitive(res);
    }
}
