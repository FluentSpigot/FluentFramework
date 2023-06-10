package io.github.jwdeveloper.ff.extension.gui.api.events;

import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import lombok.Value;
import org.bukkit.entity.Player;

@Value
public class TickGuiEvent {

    FluentInventory inventory;

    Player player;

    int tick;

}
