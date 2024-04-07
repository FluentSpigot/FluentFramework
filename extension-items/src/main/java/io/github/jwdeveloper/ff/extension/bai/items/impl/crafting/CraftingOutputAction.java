package io.github.jwdeveloper.ff.extension.bai.items.impl.crafting;

import io.github.jwdeveloper.ff.extension.bai.items.api.crafting.FluentCrafting;
import org.bukkit.inventory.ItemStack;

public interface CraftingOutputAction
{
     ItemStack onOutput(FluentCrafting fluentCrafting, ItemStack[] matrix);
}
