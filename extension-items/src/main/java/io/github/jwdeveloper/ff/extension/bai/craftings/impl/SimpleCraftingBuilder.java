package io.github.jwdeveloper.ff.extension.bai.craftings.impl;

import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCraftingRegistry;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCrafting;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.builder.FluentCraftingBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;
import java.util.function.Supplier;

public class SimpleCraftingBuilder implements FluentCraftingBuilder {

    private final FluentCrafting crafting;
    private final FluentCraftingRegistry registry;

    public SimpleCraftingBuilder(FluentCrafting crafting, FluentCraftingRegistry registry) {
        this.crafting = crafting;
        this.registry = registry;
    }

    @Override
    public FluentCraftingBuilder withOutput(ItemStack itemStack) {
        crafting.setCraftingOutputAction((fluentCrafting, matrix) -> itemStack);

        return this;
    }

    @Override
    public FluentCraftingBuilder withOutput(FluentItem fluentItem) {
        crafting.setFluentItem(fluentItem);
        crafting.setCraftingOutputAction((fluentCrafting, matrix) -> fluentItem.toItemStack());
        return this;
    }

    @Override
    public FluentCraftingBuilder withOutput(Supplier<ItemStack> outputItemStack) {
        crafting.setCraftingOutputAction((fluentCrafting, matrix) -> fluentCrafting.getFluentItem().toItemStack());
        return this;
    }

    @Override
    public FluentCraftingBuilder withOutput(CraftingOutputAction outputItemStack) {
        crafting.setCraftingOutputAction(outputItemStack);
        return this;
    }

    @Override
    public FluentCraftingBuilder setSlot(int index, Material material) {
        crafting.getSlotsMaterials().put(index, material);
        return this;
    }

    @Override
    public FluentCraftingBuilder setSlot(int index, ItemStack itemStack) {
        crafting.getSlotsMaterials().put(index, itemStack.getType());
        crafting.getSlotsValidators().put(index, itemStack1 -> itemStack1.isSimilar(itemStack));
        return this;
    }

    @Override
    public FluentCraftingBuilder setSlot(int index, FluentItem fluentItem) {
        crafting.getSlotsMaterials().put(index, fluentItem.getSchema().getMaterial());
        crafting.getSlotsValidators().put(index, fluentItem::isItemStack);
        return this;
    }

    @Override
    public FluentCraftingBuilder setSlot(int index, Function<ItemStack, Boolean> validation) {
        crafting.getSlotsValidators().put(index, validation);
        return this;
    }

    public FluentCrafting build() {
        return crafting;
    }

    @Override
    public FluentCrafting buildAndRegister() {
        var crafting = build();
        registry.register(crafting);
        return crafting;
    }
}