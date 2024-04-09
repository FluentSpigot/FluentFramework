package io.github.jwdeveloper.ff.extension.bai.craftings.api;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.builder.FluentCraftingBuilder;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import org.bukkit.inventory.ItemStack;

public interface FluentCraftingApi {
    FluentCraftingBuilder addCrafting();

    ActionResult<FluentCrafting> findCrafting(FluentItem fluentItem);

    ActionResult<FluentCrafting> findCrafting(String identifier);

    ActionResult<FluentCrafting> findCrafting(ItemStack[] craftingMatrix);
}
