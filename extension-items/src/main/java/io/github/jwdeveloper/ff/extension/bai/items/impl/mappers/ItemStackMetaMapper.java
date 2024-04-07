package io.github.jwdeveloper.ff.extension.bai.items.impl.mappers;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.extension.bai.common.FrameworkSettings;
import io.github.jwdeveloper.ff.extension.bai.items.api.itemstack.FluentItemStackMeta;
import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemSchema;
import io.github.jwdeveloper.ff.extension.bai.items.impl.itemstack.SimpleItemStackMeta;
import io.github.jwdeveloper.ff.extension.bai.items.impl.mappers.utils.PDCHelper;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;


public class ItemStackMetaMapper {
    private final FrameworkSettings settings;

    public ItemStackMetaMapper(FrameworkSettings settings) {
        this.settings = settings;
    }

    public ActionResult<FluentItemStackMeta> fromItemMeta(ItemMeta itemMeta) {

        var fluentMeta = new SimpleItemStackMeta();
        try {
            var helper = new PDCHelper(itemMeta.getPersistentDataContainer());
            helper.setThrowIfNotFound(true);
            helper.find(settings.getPluginSessionKey(), fluentMeta::setPluginSession);
            helper.find(settings.getIdKey(), fluentMeta::setUniqueId);
            helper.find(settings.getNameKey(), fluentMeta::setUniqueName);
            helper.find(settings.getPluginVersionKey(), fluentMeta::setPluginVersion);
            helper.find(settings.getTagKey(), fluentMeta::setTag);
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
        helper.set(settings.getIdKey(), id);
        helper.set(settings.getPluginVersionKey(), schema.getName());
        helper.set(settings.getPluginSessionKey(), settings.getPluginSessionId());
        helper.set(settings.getTagKey(), schema.getTag());
        helper.set(settings.getNameKey(), schema.getName());

        for (var property : schema.getProperties().entrySet()) {
            helper.set(settings.getStoragePropertyKey(property.getKey()), property.getValue());
        }

        return ActionResult.success(meta);
    }
}
