package io.github.jwdeveloper.ff.extension.bai.items.impl.events;

import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class FluentItemCreateEvent extends FluentItemEvent {

    private final ItemStack itemStack;

    public FluentItemCreateEvent(FluentItem fluentItem, ItemStack itemStack)
    {
        super(fluentItem);
        this.itemStack = itemStack;
    }
}
