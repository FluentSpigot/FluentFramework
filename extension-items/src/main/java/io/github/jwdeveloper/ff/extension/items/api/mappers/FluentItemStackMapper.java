package io.github.jwdeveloper.ff.extension.items.api.mappers;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.extension.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.items.api.itemstack.FluentItemStack;
import io.github.jwdeveloper.ff.extension.items.api.schema.FluentItemSchema;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public interface FluentItemStackMapper {
    ItemStack toItemStack(FluentItemSchema itemSchema);
    ActionResult<FluentItemStack> fromItemStack(ItemStack itemStack);
}
