package io.github.jwdeveloper.ff.extension.gui.core.api.managers.events;


import io.github.jwdeveloper.ff.extension.gui.core.api.FluentInventory;
import lombok.Getter;
import new_version.implementation.buttons.ButtonUI;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@Getter
public class ClickEvent implements Cancellable {

    private final Player player;
    private final ButtonUI button;
    private final FluentInventory inventory;

    public ClickEvent(Player player, ButtonUI button, FluentInventory inventory) {
        this.player = player;
        this.button = button;
        this.inventory = inventory;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }
}
