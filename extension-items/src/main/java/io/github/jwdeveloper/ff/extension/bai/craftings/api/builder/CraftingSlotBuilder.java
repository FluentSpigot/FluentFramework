package io.github.jwdeveloper.ff.extension.bai.craftings.api.builder;

import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public interface CraftingSlotBuilder<T>
{
    T setSlot(int index, Material material);

    T setSlot(int index, ItemStack itemStack);

    T setSlot(int index, FluentItem fluentItem);

    T setSlot(int index, Function<ItemStack, Boolean> validation);
}
