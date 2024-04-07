package io.github.jwdeveloper.ff.extension.bai.items.api.itemstack;

import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import org.bukkit.inventory.ItemStack;

public interface FluentItemStack {
    FluentItemStackMeta getMeta();
    String getId();
    String getName();
    FluentItem getFluentItem();
    ItemStack getItemStack();
    <T> T getProperty(String key, Class<T> clazz);
    <T> T getPropertyOrDefault(String key, T _default);
    void setProperty(String key, Object value);
    void setPropertyIfEmpty(String key, Object value);
}
