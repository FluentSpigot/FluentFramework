package io.github.jwdeveloper.ff.extension.gui.core.api;
import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.buttons.ButtonManager;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.events.EventsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.ChildrenManager;
import io.github.jwdeveloper.ff.extension.gui.core.api.managers.permissions.PermissionManager;

public interface FluentInventory
{
    Player getPlayer();
    void open(Player player, Object... args);

    void close();

    void refresh();

    boolean click(InventoryClickEvent event);

    void drag(InventoryDragEvent event);

    void setTitle(String title);

    ChildrenManager children();

    ButtonManager buttons();

    EventsManager events();

    PermissionManager permissions();
    SimpleLogger logger();
}
