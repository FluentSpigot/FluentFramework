package io.github.jwdeveloper.ff.extension.items.impl.itemstack;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.extension.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.items.api.itemstack.FluentItemStack;
import io.github.jwdeveloper.ff.extension.items.api.itemstack.FluentItemStackMeta;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

@Getter
public class SimpleItemStack implements FluentItemStack {
    private final ItemStack itemStack;
    private final FluentItem fluentItem;
    private final FluentItemStackMeta meta;
    private final SimpleItemStackProperties simpleItemStackProperties;

    public SimpleItemStack(ItemStack itemStack,
                           FluentItem fluentItem,
                           FluentItemStackMeta fluentItemStackMeta,
                           SimpleItemStackProperties simpleItemStackProperties) {
        this.itemStack = itemStack;
        this.fluentItem = fluentItem;
        this.meta = fluentItemStackMeta;
        this.simpleItemStackProperties = simpleItemStackProperties;
    }

    @Override
    public String getId() {
        return meta.getUniqueId();
    }

    @Override
    public String getName() {
        return meta.getUniqueName();
    }

    @Override
    public <T> T getProperty(String key, Class<T> clazz) {
        return simpleItemStackProperties.getProperty(key, clazz);
    }

    @Override
    public <T> T getPropertyOrDefault(String key, T _default) {
        return simpleItemStackProperties.getPropertyOrDefault(key, _default);
    }

    @Override
    public void setProperty(String key, Object value) {
        simpleItemStackProperties.setProperty(key, value);
    }

    @Override
    public void setPropertyIfEmpty(String key, Object value) {
        simpleItemStackProperties.setPropertyIfEmpty(key, value);
    }


}
