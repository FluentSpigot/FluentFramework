package io.github.jwdeveloper.ff.extension.bai.craftings.impl;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCrafting;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCraftingApi;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.builder.FluentCraftingBuilder;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCraftingRegistry;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import org.bukkit.inventory.ItemStack;

public class SimpleCraftingApi implements FluentCraftingApi {
    private final FluentCraftingRegistry registry;

    public SimpleCraftingApi(FluentCraftingRegistry registry) {
        this.registry = registry;
    }

    public FluentCraftingBuilder addCrafting() {
        var crafting = new FluentCrafting("test");
        return new SimpleCraftingBuilder(crafting, registry);
    }

    public ActionResult<FluentCrafting> findCrafting(FluentItem fluentItem) {
        return ActionResult.fromOptional(registry.findByFluentItem(fluentItem));
    }

    public ActionResult<FluentCrafting> findCrafting(String identifier) {
        return ActionResult.fromOptional(registry.findByName(identifier));
    }

    public ActionResult<FluentCrafting> findCrafting(ItemStack[] craftingMatrix) {
        return ActionResult.fromOptional(registry.findFirstMatch(craftingMatrix));
    }
}
