package io.github.jwdeveloper.ff.extension.items.spigot.listeners;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import io.github.jwdeveloper.ff.extension.items.api.crafting.FluentCraftingRegistry;
import io.github.jwdeveloper.ff.extension.items.impl.events.FluentItemCraftEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.plugin.Plugin;


public class CraftingListener extends EventBase {

    private final FluentCraftingRegistry craftingRegistry;

    public CraftingListener(Plugin plugin, FluentCraftingRegistry craftingRegistry) {
        super(plugin);
        this.craftingRegistry = craftingRegistry;
    }

    @EventHandler
    public void onCrafting(PrepareItemCraftEvent event) {

        var matrix = event.getInventory().getMatrix();

        var optional = craftingRegistry.findFirstMatch(matrix);
        if (optional.isEmpty()) {
            return;
        }
        var crafting = optional.get();
        var fluentItem = crafting.getFluentItem();
        var itemStack = crafting.getOutput(matrix);
        var craftingEvent = new FluentItemCraftEvent(fluentItem, itemStack);
        fluentItem.events().getOnCrafting().invoke(craftingEvent);
        if (craftingEvent.isCanceled()) {
            event.getInventory().setResult(null);
            return;
        }
        if (event.getInventory().getViewers().get(0) instanceof Player player) {
            if (!fluentItem.isPlayerCanUse(player)) {
                event.getInventory().setResult(null);
                return;
            }
        }
        event.getInventory().setResult(itemStack);
    }
}
