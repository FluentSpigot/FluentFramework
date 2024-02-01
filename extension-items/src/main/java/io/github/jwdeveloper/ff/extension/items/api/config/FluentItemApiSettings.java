package io.github.jwdeveloper.ff.extension.items.api.config;

import io.github.jwdeveloper.ff.extension.items.api.schema.FluentItemSchema;
import lombok.Data;
import org.bukkit.NamespacedKey;

import java.util.UUID;

@Data
public class FluentItemApiSettings {
    NamespacedKey pluginVersionKey;
    NamespacedKey pluginSessionKey;

    NamespacedKey itemIdKey;
    NamespacedKey itemNameKey;
    NamespacedKey itemStorageKey;
    NamespacedKey itemTagKey;

    UUID pluginSessionId;
    FluentItemSchema defaultSchema;


    public NamespacedKey getStoragePropertyKey(String propertyName) {
        return new NamespacedKey(itemStorageKey.getNamespace(), itemStorageKey.getKey() + "." + propertyName.toLowerCase());
    }
}
