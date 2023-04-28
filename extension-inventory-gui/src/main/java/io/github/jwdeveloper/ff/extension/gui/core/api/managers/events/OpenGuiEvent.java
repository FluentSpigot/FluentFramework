package io.github.jwdeveloper.ff.extension.gui.core.api.managers.events;

import io.github.jwdeveloper.ff.extension.gui.core.api.FluentInventory;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class OpenGuiEvent implements Cancellable {

    @Getter
    @Setter
    private boolean Cancelled;
    @Getter
    private FluentInventory inventory;

    @Getter
    private Player player;
}
