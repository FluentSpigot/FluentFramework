package io.github.jwdeveloper.ff.extension.items.impl.events;

import io.github.jwdeveloper.ff.extension.items.api.FluentItem;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class FluentItemSpawnEvent extends FluentItemEvent {

    private ItemStack itemStack;

    public FluentItemSpawnEvent(FluentItem fluentItem, ItemStack itemStack) {
        super(fluentItem);
        this.itemStack = itemStack;
    }
}
