package io.github.jwdeveloper.ff.extension.gui.api;


import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public interface FluentInventoryFactory {
    FluentInventory create(InventoryComponent... components);

    FluentInventory create(Class<? extends InventoryComponent>... componentsTypes);
    List<FluentInventory> find(UUID uuid, Class<? extends InventoryComponent>... componentsTypes);

    List<FluentInventory> find(Player uuid, Class<? extends InventoryComponent>... componentsTypes);
    FluentInventory findFirst(UUID uuid, Class<? extends InventoryComponent>... componentsTypes);

    FluentInventory findFirst(Player uuid, Class<? extends InventoryComponent>... componentsTypes);

}
