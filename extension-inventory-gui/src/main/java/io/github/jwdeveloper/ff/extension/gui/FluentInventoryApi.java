package io.github.jwdeveloper.ff.extension.gui;

import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventorySettings;
import io.github.jwdeveloper.ff.extension.gui.implementation.InventoryDecoratorImpl;
import io.github.jwdeveloper.ff.extension.gui.implementation.managers.ButtonManagerImpl;
import io.github.jwdeveloper.ff.extension.gui.implementation.managers.ComponentsManagerImpl;
import io.github.jwdeveloper.ff.extension.gui.implementation.managers.EventManagerImpl;
import io.github.jwdeveloper.ff.extension.gui.implementation.managers.PermissionManagerImpl;
import io.github.jwdeveloper.ff.extension.gui.implementation.FluentInventoryImpl;
import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.events.InventorySpigotListener;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class FluentInventoryApi {
    private final Plugin plugin;
    private final InventorySpigotListener spigotListener;
    private final FluentTranslator translator;

    public FluentInventoryApi(Plugin plugin, FluentTranslator translator) {
        this.plugin = plugin;
        this.translator = translator;
        spigotListener = new InventorySpigotListener(plugin);
    }

    public FluentInventory findOrCreate(Player player, InventoryComponent... components) {
        return findOrCreate(player.getUniqueId(), components);
    }

    public FluentInventory findOrCreate(UUID uuid, InventoryComponent... components) {
        var settings = new InventorySettings();
        var logger =  new SimpleLogger();
        var inventory = new FluentInventoryImpl
                (
                        new ComponentsManagerImpl(logger),
                        new ButtonManagerImpl(settings),
                        new EventManagerImpl(),
                        new PermissionManagerImpl(settings),
                        settings,
                        logger
                );
        var decorator = new InventoryDecoratorImpl(inventory);
        for (var component : components) {
            decorator.withComponent(component);
        }
        decorator.apply();
        return inventory;
    }

}
