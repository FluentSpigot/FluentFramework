package io.github.jwdeveloper.ff.extension.items.impl.crafting;

import io.github.jwdeveloper.ff.extension.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.items.api.crafting.FluentCrafting;
import org.bukkit.inventory.ItemStack;

public interface CraftingOutputAction
{
     ItemStack onOutput(FluentCrafting fluentCrafting, ItemStack[] matrix);
}
