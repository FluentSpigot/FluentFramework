package io.github.jwdeveloper.ff.extension.bai.items.impl.itemstack;

import io.github.jwdeveloper.ff.core.files.json.JsonUtility;
import io.github.jwdeveloper.ff.extension.bai.common.FrameworkSettings;
import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemSchema;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class SimpleItemStackProperties {
    private final FrameworkSettings settings;
    private final ItemStack itemStack;
    private final FluentItemSchema schema;

    public SimpleItemStackProperties(FrameworkSettings settings,
                                     ItemStack itemStack,
                                     FluentItemSchema schema) {
        this.settings = settings;
        this.itemStack = itemStack;
        this.schema = schema;
    }

    public <T> T getProperty(String key, Class<T> clazz) {
        var container = itemStack.getItemMeta().getPersistentDataContainer();
        if (container.has(settings.getStoragePropertyKey(key), PersistentDataType.STRING)) {
            var value = container.get(settings.getStoragePropertyKey(key), PersistentDataType.STRING);
            return (T) castValueToType(value, clazz);
        }

        if (schema.getProperties().containsKey(key)) {
            var value = schema.getProperties().get(key);
            return (T) value;
        }

        throw new RuntimeException("Property " + key + " not found!");
    }


    public <T> T getPropertyOrDefault(String key, T _default) {
        try {
            return (T) getProperty(key, _default.getClass());
        } catch (Exception e) {
            return _default;
        }
    }


    private Object castValueToType(String value, Class type) {
        if (type == String.class) {
            return value;
        }
        if (!type.isPrimitive()) {
            var gson = JsonUtility.getGson();
            return gson.fromJson(value, type);
        }
        if (type.isEnum()) {
            return Enum.valueOf(type, value);
        }
        if (type == Boolean.class || type == boolean.class) {
            return Boolean.parseBoolean(value);
        }
        if (type == Number.class) {
            return Double.parseDouble(value);
        }
        if (type == Integer.class || type == int.class) {
            return Integer.parseInt(value);
        }
        if (type == Float.class || type == float.class) {
            return Float.parseFloat(value);
        }

        throw new RuntimeException("Unsupported parsing type " + type);
    }


    public void setProperty(String key, Object value) {
        var meta = itemStack.getItemMeta();
        var container = meta
                .getPersistentDataContainer();
        container.set(settings.getStoragePropertyKey(key), PersistentDataType.STRING, value.toString());
        itemStack.setItemMeta(meta);
    }

    public void setPropertyIfEmpty(String key, Object value) {
        var container = itemStack.getItemMeta()
                .getPersistentDataContainer();
        if (container.has(settings.getStoragePropertyKey(key), PersistentDataType.STRING)) {
            return;
        }
        setProperty(key, value);
    }


}
