package io.github.jwdeveloper.ff.extension.items.api.crafting;

import io.github.jwdeveloper.ff.extension.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.items.impl.crafting.CraftingOutputAction;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;
import java.util.function.Supplier;

public interface FluentCraftingBuilder {
    FluentCraftingBuilder withOutput(ItemStack itemStack);

    FluentCraftingBuilder withOutput(FluentItem fluentItem);

    FluentCraftingBuilder withOutput(Supplier<ItemStack> outputItemStack);

    FluentCraftingBuilder withOutput(CraftingOutputAction outputItemStack);

    FluentCraftingBuilder setSlot(int index, Material material);

    FluentCraftingBuilder setSlot(int index, ItemStack itemStack);

    FluentCraftingBuilder setSlot(int index, FluentItem fluentItem);

    FluentCraftingBuilder setSlot(int index, Function<ItemStack, Boolean> validation);
}
