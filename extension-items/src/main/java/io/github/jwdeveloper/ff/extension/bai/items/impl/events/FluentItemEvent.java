package io.github.jwdeveloper.ff.extension.bai.items.impl.events;

import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import lombok.Getter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class FluentItemEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean canceled = false;

    private final FluentItem fluentItem;

    public FluentItemEvent(FluentItem fluentItem) {
        this.fluentItem = fluentItem;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }



    public static HandlerList getHandlerList() {
        return handlers;
    }


    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        canceled = cancel;
    }


}
