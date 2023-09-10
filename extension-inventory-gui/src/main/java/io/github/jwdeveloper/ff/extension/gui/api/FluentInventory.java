package io.github.jwdeveloper.ff.extension.gui.api;

import io.github.jwdeveloper.ff.core.logger.plugin.SimpleLogger;
import io.github.jwdeveloper.ff.extension.gui.api.managers.ComponentsManager;
import io.github.jwdeveloper.ff.extension.gui.api.managers.EventsManager;
import io.github.jwdeveloper.ff.extension.gui.api.managers.buttons.ButtonManager;
import io.github.jwdeveloper.ff.extension.gui.api.managers.permissions.PermissionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public interface FluentInventory {
    Player getPlayer();

    void open(Player player, Object... args);

    void close();

    void refresh();

    boolean click(InventoryClickEvent event);

    void drag(InventoryDragEvent event);

    void setTitle(String title);

    ButtonManager buttons();

    EventsManager events();

    PermissionManager permissions();

    ComponentsManager components();

    FluentInventory parent();

    boolean hasParent();

    void setParent(FluentInventory parent);

    InventorySettings settings();

    SimpleLogger logger();
}
