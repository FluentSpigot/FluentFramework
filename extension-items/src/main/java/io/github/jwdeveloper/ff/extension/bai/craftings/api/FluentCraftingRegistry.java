package io.github.jwdeveloper.ff.extension.bai.craftings.api;

import io.github.jwdeveloper.ff.core.common.registry.Registry;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

public interface FluentCraftingRegistry extends Registry<FluentCrafting>
{
    void register(FluentCrafting fluentCrafting);

    void unregister(FluentCrafting fluentCrafting);

    List<FluentCrafting> findMatch(ItemStack[] matrix);
    Optional<FluentCrafting> findFirstMatch(ItemStack[] matrix);
    Optional<FluentCrafting> findByFluentItem(FluentItem fluentItem);

    void reset();
}
