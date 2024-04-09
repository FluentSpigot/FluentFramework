package io.github.jwdeveloper.ff.extension.bai.items.impl.events;

import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import org.bukkit.inventory.ItemStack;

public class FluentItemCraftEvent extends FluentItemEvent {

    private final ItemStack itemStack;

    public FluentItemCraftEvent(FluentItem fluentItem, ItemStack itemStack)
    {
        super(fluentItem);
        this.itemStack = itemStack;
    }
}
