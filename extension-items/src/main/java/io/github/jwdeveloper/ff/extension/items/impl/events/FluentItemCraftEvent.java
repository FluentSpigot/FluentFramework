package io.github.jwdeveloper.ff.extension.items.impl.events;

import io.github.jwdeveloper.ff.extension.items.api.FluentItem;
import org.bukkit.inventory.ItemStack;

public class FluentItemCraftEvent extends FluentItemEvent {

    private final ItemStack itemStack;

    public FluentItemCraftEvent(FluentItem fluentItem, ItemStack itemStack)
    {
        super(fluentItem);
        this.itemStack = itemStack;
    }
}
