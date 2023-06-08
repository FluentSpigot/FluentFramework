package io.github.jwdeveloper.ff.extension.gui.api.events;

import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;

@Getter
@AllArgsConstructor
public class CreateGuiEvent implements Cancellable {

    @Setter
    private boolean cancelled;

    private Player player;

    private FluentInventory inventory;

    private InventoryDecorator decorator;


}
