package io.github.jwdeveloper.ff.extension.bai.craftings.impl;

import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCrafting;
import org.bukkit.inventory.ItemStack;

public interface CraftingOutputAction
{
     ItemStack onOutput(FluentCrafting fluentCrafting, ItemStack[] matrix);
}
