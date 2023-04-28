package io.github.jwdeveloper.ff.extension.gui.core;

import io.github.jwdeveloper.ff.extension.gui.core.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.core.implementation.InventoryDecoratorImpl;
import new_version.implementation.FluentInventoryImpl;
import new_version.implementation.InventorySpigotListener;
import io.github.jwdeveloper.ff.core.injector.api.containers.PlayersContainer;
import io.github.jwdeveloper.ff.core.translator.api.FluentTranslator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class FluentInventoryApi
{
    private final Plugin plugin;
    private final InventorySpigotListener spigotListener;
    private final FluentTranslator translator;
    private final PlayersContainer playerContext;

    public FluentInventoryApi(Plugin plugin, FluentTranslator translator, PlayersContainer playerContext)
    {
        this.playerContext = playerContext;
        this.plugin = plugin;
        this.translator = translator;
        spigotListener = new InventorySpigotListener(plugin);
    }


    public FluentInventory findOrCreate(UUID uuid, InventoryComponent inventoryComponent)
    {
        var inventory = new FluentInventoryImpl
                (
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );
        var decotrator = new InventoryDecoratorImpl(inventory);
        decotrator.withComponent(null);
        decotrator.apply();
        return inventory;
    }

    public <T> FluentInventory findOrCreate(Player player, Class<T> inventoryComponentClass)
    {
        return null;
    }
}
