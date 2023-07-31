package io.github.jwdeveloper.ff.extension.gui.implementation;

import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.extension.gui.api.*;
import io.github.jwdeveloper.ff.extension.gui.api.managers.ComponentsManager;
import io.github.jwdeveloper.ff.extension.gui.implementation.managers.*;
import org.bukkit.entity.Player;

import java.util.*;

public class FluentInventoryFactoryImpl implements FluentInventoryFactory {
    private final InventoryApi fluentInventoryApi;
    private final FluentTaskFactory fluentTaskManager;
    private final Set<FluentInventory> inventories;

    public FluentInventoryFactoryImpl(InventoryApi fluentInventoryApi, FluentTaskFactory fluentTaskManager) {
        this.fluentInventoryApi = fluentInventoryApi;
        this.fluentTaskManager = fluentTaskManager;
        inventories = new HashSet<>();
    }

    @Override
    public FluentInventory create(InventoryComponent... components) {
        var settings = new InventorySettings();
        var logger = new SimpleLogger();
        var inventory = new FluentInventoryImpl
                (
                        new ComponentsManagerImpl(logger, List.of(components)),
                        new ButtonManagerImpl(settings),
                        new EventManagerImpl(),
                        new PermissionManagerImpl(settings),
                        settings,
                        logger,
                        fluentInventoryApi,
                        new TickManagerImpl(settings, fluentTaskManager)
                );
        return inventory;
    }

    @Override
    public FluentInventory create(Class<? extends InventoryComponent>... componentsTypes)
    {
        return null;
    }

    public List<FluentInventory> find(UUID uuid, Class<? extends InventoryComponent>... componentsTypes) {
        var result = new ArrayList<FluentInventory>();
        for (var inventory : inventories) {
            if (inventory.getPlayer() == null) {
                continue;
            }
            if (!inventory.getPlayer().getUniqueId().equals(uuid)) {
                continue;
            }
            if (!hasComponents(inventory.components(), componentsTypes)) {
                continue;
            }
            result.add(inventory);
        }
        return result;
    }

    public List<FluentInventory> find(Player player, Class<? extends InventoryComponent>... componentsTypes) {
        return find(player.getUniqueId(), componentsTypes);
    }

    @Override
    public FluentInventory findFirst(UUID uuid, Class<? extends InventoryComponent>... componentsTypes) {
        var result = find(uuid, componentsTypes);
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public FluentInventory findFirst(Player player, Class<? extends InventoryComponent>... componentsTypes) {
        return findFirst(player.getUniqueId(), componentsTypes);
    }


    private boolean hasComponents(ComponentsManager componentsManager, Class<? extends InventoryComponent>... componentsTypes) {
        for (var componentType : componentsTypes) {
            var component = componentsManager.find(componentType);
            if (component == null) {
                return false;
            }
        }
        return true;
    }

}
