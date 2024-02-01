package io.github.jwdeveloper.ff.extension.items.impl.events;

import io.github.jwdeveloper.ff.extension.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.items.api.FluentItemEvents;
import io.github.jwdeveloper.ff.extension.items.api.itemstack.FluentItemStack;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class FluentItemDropEvent extends FluentItemEvent {

    private final Player player;
    private final FluentItemStack fluentItemStack;

    public FluentItemDropEvent(Player player, FluentItemStack fluentItemStack) {
        super(fluentItemStack.getFluentItem());
        this.fluentItemStack = fluentItemStack;
        this.player = player;
    }
}
