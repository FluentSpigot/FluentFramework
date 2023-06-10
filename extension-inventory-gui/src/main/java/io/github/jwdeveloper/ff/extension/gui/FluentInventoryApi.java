package io.github.jwdeveloper.ff.extension.gui;

import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventorySettings;
import io.github.jwdeveloper.ff.extension.gui.implementation.FluentInventoryImpl;
import io.github.jwdeveloper.ff.extension.gui.implementation.InventoryApiImpl;
import io.github.jwdeveloper.ff.extension.gui.implementation.InventoryDecoratorImpl;
import io.github.jwdeveloper.ff.extension.gui.implementation.button_old.events.InventorySpigotListener;
import io.github.jwdeveloper.ff.extension.gui.implementation.managers.*;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.InventoryComponentFactoryImpl;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.ButtonWidgetFactoryImpl;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class FluentInventoryApi {
    private final Plugin plugin;
    private final InventorySpigotListener spigotListener;
    private final FluentTranslator translator;
    private final InventoryApi inventoryApi;

    public FluentInventoryApi(Plugin plugin, FluentTranslator translator) {
        this.plugin = plugin;
        this.translator = translator;
        spigotListener = new InventorySpigotListener(plugin);
        inventoryApi = new InventoryApiImpl(translator, new ButtonWidgetFactoryImpl(), new InventoryComponentFactoryImpl());
    }

    public FluentInventory findOrCreate(Player player, InventoryComponent... components) {
        return findOrCreate(player.getUniqueId(), components);
    }

    public FluentInventory findOrCreate(UUID uuid, InventoryComponent... components) {
        var settings = new InventorySettings();
        var logger = new SimpleLogger();
        var tasks = FluentApi.tasks();
        var inventory = new FluentInventoryImpl
                (
                        new ComponentsManagerImpl(logger),
                        new ButtonManagerImpl(settings),
                        new EventManagerImpl(),
                        new PermissionManagerImpl(settings),
                        settings,
                        logger,
                        inventoryApi,
                        new TickManagerImpl(settings, tasks)
                );
        var decorator = new InventoryDecoratorImpl(inventory, inventoryApi);
        for (var component : components) {
            decorator.withComponent(component);
        }
        decorator.apply();
        return inventory;
    }

}
