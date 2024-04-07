package io.github.jwdeveloper.ff.extension.bai.items.impl.events;

import io.github.jwdeveloper.ff.extension.bai.items.api.itemstack.FluentItemStack;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Getter
public class FluentItemUseEvent extends FluentItemEvent {

    private final Player player;
    private final Action action;
    private final FluentItemStack fluentItemStack;
    private final Event spigotEvent;

    public FluentItemUseEvent(Player player, FluentItemStack fluentItemStack, Action action, Event event) {
        super(fluentItemStack.getFluentItem());
        this.player = player;
        this.action = action;
        this.fluentItemStack = fluentItemStack;
        this.spigotEvent = event;
    }


    public enum Action {
        CLICK, LEFT, RIGHT, SHIFT, SHIFT_LEFT, SHIFT_RIGHT;

        public boolean isShift() {
            return this.name().contains("SHIFT");
        }
    }
}
