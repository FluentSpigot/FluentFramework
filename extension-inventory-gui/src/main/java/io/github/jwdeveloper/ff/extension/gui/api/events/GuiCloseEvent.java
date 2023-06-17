package io.github.jwdeveloper.ff.extension.gui.api.events;

import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@AllArgsConstructor
@Getter
public class GuiCloseEvent implements Cancellable {

    @Setter
    private boolean Cancelled;

    private FluentInventory inventory;

    private Player player;


}
