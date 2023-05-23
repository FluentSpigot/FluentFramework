package io.github.jwdeveloper.ff.extension.gui.inventory;

import io.github.jwdeveloper.ff.extension.gui.core.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryDecorator;
import io.github.jwdeveloper.ff.plugin.api.features.FluentTranslator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class InventoryComponent {

    private Plugin plugin;
    private FluentInventory inventory;
    private FluentTranslator fluentTranslator;

    void constructor(Plugin plugin,
                     FluentInventory inventory,
                     FluentTranslator translator)

    {
        this.fluentTranslator = translator;
        this.plugin = plugin;
        this.inventory = inventory;
    }

    public abstract void onInitialize(InventoryDecorator decorator);

    protected final Plugin plugin()
    {
        return plugin;
    }

    protected final String translate(String key) {
        return fluentTranslator.get(key);
    }

    public final FluentInventory inventory() {
        return inventory;
    }

    public final Player player()
    {
        return inventory().getPlayer();
    }

}
