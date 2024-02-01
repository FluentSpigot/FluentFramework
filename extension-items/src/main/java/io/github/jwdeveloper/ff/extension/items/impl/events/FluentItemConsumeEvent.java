package io.github.jwdeveloper.ff.extension.items.impl.events;

import io.github.jwdeveloper.ff.extension.items.api.FluentItem;
import org.bukkit.entity.Player;

public class FluentItemConsumeEvent extends FluentItemEvent
{
    private final Player player;

    public FluentItemConsumeEvent(Player player, FluentItem fluentItem) {
        super(fluentItem);
        this.player = player;
    }
}
