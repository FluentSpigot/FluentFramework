package io.github.jwdeveloper.ff.extension.bai.items.api.mappers;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.extension.bai.items.api.itemstack.FluentItemStack;
import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemSchema;
import org.bukkit.inventory.ItemStack;

public interface FluentItemStackMapper {
    ItemStack toItemStack(FluentItemSchema itemSchema);
    ActionResult<FluentItemStack> fromItemStack(ItemStack itemStack);
}
