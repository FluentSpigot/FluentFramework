package io.github.jwdeveloper.ff.extension.gui.core.api;

import io.github.jwdeveloper.ff.extension.gui.core.api.enums.InventoryState;
import lombok.Data;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

@Data
public class InventorySettings {
    public static int INVENTORY_WIDTH = 9;

    private String title = "Inventory";
    private int height = 1;

    private InventoryState state = InventoryState.NOT_CREATED;

    private InventoryType inventoryType = InventoryType.CHEST;

    private Inventory handle;

    private boolean inactive;

    private List<String> permissions = new ArrayList<>();

    public int getSlots() {
        return height * INVENTORY_WIDTH;
    }

    public int getWidth()
    {
        return INVENTORY_WIDTH;
    }
}
