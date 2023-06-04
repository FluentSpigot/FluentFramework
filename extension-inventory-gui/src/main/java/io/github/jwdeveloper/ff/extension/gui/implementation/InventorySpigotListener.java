package io.github.jwdeveloper.ff.extension.gui.implementation;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import io.github.jwdeveloper.ff.extension.gui.core.api.enums.InventoryState;
import io.github.jwdeveloper.ff.extension.gui.implementation.events.SpigotListenerActionEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.LinkedList;
import java.util.List;

public class InventorySpigotListener extends EventBase {
    private final List<FluentInventoryImpl> inventoriesGui;

    public InventorySpigotListener(Plugin plugin) {
        super(plugin);
        inventoriesGui = new LinkedList<>();
    }

    @EventHandler
    private void onInventoryRequest(SpigotListenerActionEvent request)
    {
        switch (request.getState())
        {
            case Register ->
            {
                if (inventoriesGui.contains(request.getInventory())) {
                    return;
                }
                inventoriesGui.add(request.getInventory());
            }
            case Unregister ->
            {
                inventoriesGui.remove(request.getInventory());
            }
        }
    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event)
    {
        Inventory spigotInventory;
        for (var inventoryUI : inventoriesGui) {
            spigotInventory = inventoryUI.handle();
            if (spigotInventory == null || inventoryUI.state() == InventoryState.CLOSED) continue;
            if (event.getInventory() == spigotInventory) {
                inventoryUI.close();
                break;
            }
        }
    }


    @EventHandler
    private void onInventoryOpen(InventoryOpenEvent event) {
        Inventory inventory;
        for (var InventoryUI : inventoriesGui) {
            inventory = InventoryUI.handle();
            if (inventory == null || InventoryUI.state() != InventoryState.OPEN) continue;
            if (event.getInventory() == inventory) {
                InventoryUI.refresh();
                break;
            }
        }
    }


    @EventHandler(priority = EventPriority.LOWEST)
    private void onClick(InventoryClickEvent event) {
        if (event.getRawSlot() == -999) return;
        Inventory inventory;
        ItemStack selectedItem;
        for (var inventoryUI : inventoriesGui) {
            inventory = inventoryUI.handle();
            if (inventory == null || inventoryUI.state() != InventoryState.OPEN) continue;

            if (event.getInventory() == inventory) {
                selectedItem = event.getCurrentItem();
                if (selectedItem == null || selectedItem.getType() == Material.AIR) return;

                inventoryUI.click(event);
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void onDrag(InventoryDragEvent event) {
        if (event.getInventorySlots().size() > 2) return;

        for (var slot : event.getRawSlots()) {
            if (slot == -999) return;
        }

        Inventory inventory;
        for (var inventoryUI : inventoriesGui) {
            inventory = inventoryUI.handle();
            if (inventory == null || inventoryUI.state() != InventoryState.OPEN) continue;

            if (event.getInventory() == inventory) {
                inventoryUI.drag(event);
                break;
            }
        }
    }

    @EventHandler
    private void onPlayerExit(PlayerQuitEvent event) {
        for(var inventory : inventoriesGui)
        {
            if (event.getPlayer() != inventory.getPlayer()) {
                continue;
            }
            inventory.close();
            return;
        }
    }

    @Override
    public void onPluginStop(PluginDisableEvent event) {
        for(var inventory : inventoriesGui)
        {
            inventory.close();
        }
    }

}
