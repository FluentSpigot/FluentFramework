package io.github.jwdeveloper.ff.extension.bai.items.impl.crafting;

import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.items.api.crafting.FluentCrafting;
import io.github.jwdeveloper.ff.extension.bai.items.api.crafting.FluentCraftingBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;
import java.util.function.Supplier;

public class SimpleCraftingBuilder implements FluentCraftingBuilder {

    private final FluentCrafting crafting;

    public SimpleCraftingBuilder(FluentCrafting crafting) {
        this.crafting = crafting;
    }

    @Override
    public FluentCraftingBuilder withOutput(ItemStack itemStack) {
        crafting.setCraftingOutputAction((fluentCrafting, matrix) ->
        {
            return itemStack;
        });
        return this;
    }

    @Override
    public FluentCraftingBuilder withOutput(FluentItem fluentItem) {
        crafting.setCraftingOutputAction((fluentCrafting, matrix) ->
        {
            return fluentItem.toItemStack();
        });
        return this;
    }

    @Override
    public FluentCraftingBuilder withOutput(Supplier<ItemStack> outputItemStack) {
        crafting.setCraftingOutputAction((fluentCrafting, matrix) ->
        {
           return fluentCrafting.getFluentItem().toItemStack();
        });
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
}