package io.github.jwdeveloper.ff.extension.gui.core;

import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.extension.gui.core.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventorySettings;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.InventoryComponentBase;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.InventoryDecoratorImpl;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.managers.ButtonManagerImpl;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.managers.ChildernManagerImpl;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.managers.EventManagerImpl;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.managers.PermissionManagerImpl;
import io.github.jwdeveloper.ff.extension.gui.implementation.FluentInventoryImpl;
import io.github.jwdeveloper.ff.extension.gui.implementation.InventorySpigotListener;
import io.github.jwdeveloper.ff.core.injector.api.containers.PlayersContainer;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class FluentInventoryApi
{
    private final Plugin plugin;
    private final InventorySpigotListener spigotListener;
    private final FluentTranslator translator;

    public FluentInventoryApi(Plugin plugin, FluentTranslator translator)
    {
        this.plugin = plugin;
        this.translator = translator;
        spigotListener = new InventorySpigotListener(plugin);
    }

    public FluentInventory findOrCreate(Player player, InventoryComponent component)
    {
        return findOrCreate(player.getUniqueId(),component);
    }
    public FluentInventory findOrCreate(UUID uuid, InventoryComponent component)
    {
        var settings = new InventorySettings();
        var inventory = new FluentInventoryImpl
                (
                        new ChildernManagerImpl(),
                        new ButtonManagerImpl(settings),
                        new EventManagerImpl(),
                        new PermissionManagerImpl(settings),
                        settings,
                        new SimpleLogger()
                );
        var decotrator = new InventoryDecoratorImpl(inventory);
        decotrator.withComponent(component);
        decotrator.apply();
        return inventory;
    }

}
