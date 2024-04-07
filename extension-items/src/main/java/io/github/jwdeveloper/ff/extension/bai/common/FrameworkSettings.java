package io.github.jwdeveloper.ff.extension.bai.common;

import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemSchema;
import lombok.Data;
import org.bukkit.NamespacedKey;

import java.util.UUID;

@Data
public class FrameworkSettings {
    NamespacedKey pluginVersionKey;
    NamespacedKey pluginSessionKey;

    NamespacedKey idKey;
    NamespacedKey nameKey;
    NamespacedKey storageKey;
    NamespacedKey tagKey;

    UUID pluginSessionId;
    FluentItemSchema defaultSchema;


    public NamespacedKey getStoragePropertyKey(String propertyName) {
        return new NamespacedKey(storageKey.getNamespace(), storageKey.getKey() + "." + propertyName.toLowerCase());
    }
}
