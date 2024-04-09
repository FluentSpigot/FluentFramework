package io.github.jwdeveloper.ff.extension.bai.craftings.api.builder;

import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCrafting;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.craftings.impl.CraftingOutputAction;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;
import java.util.function.Supplier;

public interface FluentCraftingBuilder extends CraftingSlotBuilder<FluentCraftingBuilder> {
    FluentCraftingBuilder withOutput(ItemStack itemStack);

    FluentCraftingBuilder withOutput(FluentItem fluentItem);

    FluentCraftingBuilder withOutput(Supplier<ItemStack> outputItemStack);

    FluentCraftingBuilder withOutput(CraftingOutputAction outputItemStack);

    FluentCrafting build();

    FluentCrafting buildAndRegister();
}
