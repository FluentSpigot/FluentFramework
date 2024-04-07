package io.github.jwdeveloper.ff.extension.bai.items.api.crafting;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

public interface FluentCraftingRegistry
{
    void register(FluentCrafting fluentCrafting);

    void unregister(FluentCrafting fluentCrafting);

    List<FluentCrafting> findMatch(ItemStack[] matrix);
    Optional<FluentCrafting> findFirstMatch(ItemStack[] matrix);

    void reset();
}
