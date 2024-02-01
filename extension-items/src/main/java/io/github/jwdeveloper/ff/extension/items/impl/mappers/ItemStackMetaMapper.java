package io.github.jwdeveloper.ff.extension.items.impl.mappers;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.extension.items.api.config.FluentItemApiSettings;
import io.github.jwdeveloper.ff.extension.items.api.itemstack.FluentItemStackMeta;
import io.github.jwdeveloper.ff.extension.items.api.schema.FluentItemSchema;
import io.github.jwdeveloper.ff.extension.items.impl.itemstack.SimpleItemStackMeta;
import io.github.jwdeveloper.ff.extension.items.impl.mappers.utils.PDCHelper;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;


public class ItemStackMetaMapper {
    private final FluentItemApiSettings settings;

    public ItemStackMetaMapper(FluentItemApiSettings settings) {
        this.settings = settings;
    }

    public ActionResult<FluentItemStackMeta> fromItemMeta(ItemMeta itemMeta) {

        var fluentMeta = new SimpleItemStackMeta();
        try {
            var helper = new PDCHelper(itemMeta.getPersistentDataContainer());
            helper.setThrowIfNotFound(true);
            helper.find(settings.getPluginSessionKey(), fluentMeta::setPluginSession);
            helper.find(settings.getItemIdKey(), fluentMeta::setUniqueId);
            helper.find(settings.getItemNameKey(), fluentMeta::setUniqueName);
            helper.find(settings.getPluginVersionKey(), fluentMeta::setPluginVersion);
            helper.find(settings.getItemTagKey(), fluentMeta::setTag);
        } catch (Exception e) {
            return ActionResult.failed(e.getMessage());
        }

        return ActionResult.success(fluentMeta);
    }

    public ActionResult<ItemMeta> toItemMeta(ItemMeta meta, FluentItemSchema schema) {
        meta.setDisplayName(schema.getDisplayName());
        meta.setCustomModelData(schema.getCustomModelId());
        meta.setLore(schema.getLore());

        var helper = new PDCHelper(meta.getPersistentDataContainer());

        var id = schema.isStackable() ? schema.getName() : UUID.randomUUID();
        helper.set(settings.getItemIdKey(), id);
        helper.set(settings.getPluginVersionKey(), schema.getName());
        helper.set(settings.getPluginSessionKey(), settings.getPluginSessionId());
        helper.set(settings.getItemTagKey(), schema.getTag());
        helper.set(settings.getItemNameKey(), schema.getName());

        for (var property : schema.getProperties().entrySet()) {
            helper.set(settings.getStoragePropertyKey(property.getKey()), property.getValue());
        }

        return ActionResult.success(meta);
    }
}
