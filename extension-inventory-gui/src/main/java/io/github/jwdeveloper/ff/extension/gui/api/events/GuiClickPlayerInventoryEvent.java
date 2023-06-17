package io.github.jwdeveloper.ff.extension.gui.api.events;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.inventory.ItemStack;

@Getter
public class GuiClickPlayerInventoryEvent implements Cancellable {
    private Player player;
    private ItemStack buttonUI;

    public GuiClickPlayerInventoryEvent(Player player, ItemStack buttonUI) {
        this.player = player;
        this.buttonUI = buttonUI;
    }


    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean cancel) {

    }
}
