package io.github.jwdeveloper.ff.extension.bai.items.impl.events;

import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

@Getter
public class FluentItemPickupEvent  extends FluentItemEvent{

    private final ItemStack itemStack;

    private final Entity entity;

    public FluentItemPickupEvent(FluentItem fluentItem, ItemStack itemStack, Entity entity) {
        super(fluentItem);
        this.itemStack = itemStack;
        this.entity = entity;
    }
}
