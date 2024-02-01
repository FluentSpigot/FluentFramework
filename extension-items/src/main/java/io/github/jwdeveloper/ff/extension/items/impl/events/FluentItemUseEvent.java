package io.github.jwdeveloper.ff.extension.items.impl.events;

import io.github.jwdeveloper.ff.extension.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.items.api.FluentItemEvents;
import io.github.jwdeveloper.ff.extension.items.api.itemstack.FluentItemStack;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;

@Getter
public class FluentItemUseEvent extends FluentItemEvent {

    private final Player player;
    private final Action action;
    private final FluentItemStack fluentItemStack;

    public FluentItemUseEvent(Player player, FluentItemStack fluentItemStack, Action action) {
        super(fluentItemStack.getFluentItem());
        this.player = player;
        this.action = action;
        this.fluentItemStack = fluentItemStack;
    }


    public enum Action {
        CLICK, LEFT, RIGHT, SHIFT, SHIFT_LEFT, SHIFT_RIGHT;

        public boolean isShift() {
            return this.name().contains("SHIFT");
        }
    }
}
