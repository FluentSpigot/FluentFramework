package io.github.jwdeveloper.ff.extension.gui.api.events;


import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@Getter
public class GuiClickEvent implements Cancellable {

    private final Player player;
    private final ButtonUI button;
    private final FluentInventory inventory;

    private boolean isCancled;

    public GuiClickEvent(Player player, ButtonUI button, FluentInventory inventory) {
        this.player = player;
        this.button = button;
        this.inventory = inventory;
    }

    @Override
    public boolean isCancelled() {
        return isCancled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCancled = cancel;
    }
}
