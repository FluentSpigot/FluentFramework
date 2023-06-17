package io.github.jwdeveloper.ff.extension.gui.api;

import io.github.jwdeveloper.ff.extension.gui.api.enums.InventoryState;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleRenderer;
import lombok.Data;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

@Data
public class InventorySettings {
    public static int INVENTORY_WIDTH = 9;

    private String title = "Inventory";

    private String name = "Inventory";
    private int height = 6;

    private int ticksUpdate = 10;

    private InventoryState state = InventoryState.NOT_CREATED;

    private InventoryType inventoryType = InventoryType.CHEST;

    private List<String> permissions = new ArrayList<>();
    private StyleRenderer styleRenderer;
    private Inventory handle;

    private boolean inactive;

    public int getSlots() {
        return height * INVENTORY_WIDTH;
    }

    public int getWidth() {
        return INVENTORY_WIDTH;
    }

    public boolean hasStyleRenderer() {
        return styleRenderer != null;
    }

    public boolean hasHandle() {
        return handle != null;
    }
}
