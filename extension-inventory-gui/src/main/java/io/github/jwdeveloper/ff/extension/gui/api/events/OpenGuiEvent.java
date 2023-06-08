package io.github.jwdeveloper.ff.extension.gui.api.events;

import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@AllArgsConstructor
public class OpenGuiEvent implements Cancellable {

    @Getter
    @Setter
    private boolean Cancelled;
    @Getter
    private FluentInventory inventory;

    @Getter
    private Player player;

    @Getter
    private Object[] arguments;
}
