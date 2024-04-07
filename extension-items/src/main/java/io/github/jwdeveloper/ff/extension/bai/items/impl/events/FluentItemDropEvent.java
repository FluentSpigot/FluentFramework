package io.github.jwdeveloper.ff.extension.bai.items.impl.events;

import io.github.jwdeveloper.ff.extension.bai.items.api.itemstack.FluentItemStack;
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
