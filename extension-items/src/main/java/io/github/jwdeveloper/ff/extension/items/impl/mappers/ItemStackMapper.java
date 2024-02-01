package io.github.jwdeveloper.ff.extension.items.impl.mappers;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.extension.items.api.FluentItemRegistry;
import io.github.jwdeveloper.ff.extension.items.api.config.FluentItemApiSettings;
import io.github.jwdeveloper.ff.extension.items.api.itemstack.FluentItemStack;
import io.github.jwdeveloper.ff.extension.items.api.mappers.FluentItemStackMapper;
import io.github.jwdeveloper.ff.extension.items.api.schema.FluentItemSchema;
import io.github.jwdeveloper.ff.extension.items.impl.itemstack.SimpleItemStack;
import io.github.jwdeveloper.ff.extension.items.impl.itemstack.SimpleItemStackProperties;
import org.bukkit.inventory.ItemStack;

public class ItemStackMapper implements FluentItemStackMapper {
    private final FluentItemRegistry itemRegistry;
    private final FluentItemApiSettings settings;
    private final ItemStackMetaMapper itemMetaMapper;

    public ItemStackMapper(FluentItemRegistry itemRegistry, FluentItemApiSettings settings) {
        this.itemRegistry = itemRegistry;
        this.settings = settings;
        this.itemMetaMapper = new ItemStackMetaMapper(settings);
    }

    @Override
    public ItemStack toItemStack(FluentItemSchema schema) {
        var itemStack = new ItemStack(schema.getMaterial());
        var meta = itemStack.getItemMeta();
        var optional = itemMetaMapper.toItemMeta(meta, schema);
        if (optional.isFailed()) {
            throw new RuntimeException("Unable to map item meta " + optional.getMessage());
        }
        itemStack.setItemMeta(optional.getContent());
        return itemStack;
    }

    @Override
    public ActionResult<FluentItemStack> fromItemStack(ItemStack itemStack) {

        if (itemStack == null) {
            return ActionResult.failed("itemStack is null");
        }

        if (!itemStack.hasItemMeta()) {
            return ActionResult.failed("item has not have meta");
        }

        var meta = itemStack.getItemMeta();
        var optionalMeta = itemMetaMapper.fromItemMeta(meta);
        if (optionalMeta.isFailed()) {
            return optionalMeta.cast();
        }
        var fluentItemMeta = optionalMeta.getContent();
        var optional = itemRegistry.findByName(fluentItemMeta.getUniqueName());
        if (optional.isEmpty()) {
            return ActionResult.failed("Item is " + fluentItemMeta.getUniqueName() + "not registered!");
        }
        var fluentItem = optional.get();
        var properties = new SimpleItemStackProperties(settings, itemStack, fluentItem.getSchema());
        return ActionResult.success(new SimpleItemStack(itemStack,
                fluentItem,
                fluentItemMeta,
                properties
        ));
    }
}
