package io.github.jwdeveloper.ff.extension.bai.common.listeners;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCraftingRegistry;
import io.github.jwdeveloper.ff.extension.bai.items.impl.events.FluentItemCraftEvent;
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
        var itemStack = crafting.getOutput(matrix);
        var fluentItem = crafting.getFluentItem();
        if(fluentItem == null)
        {
            event.getInventory().setResult(itemStack);
            return;
        }

        var craftingEvent = new FluentItemCraftEvent(fluentItem, itemStack);
        fluentItem.events().getOnCrafting().invoke(craftingEvent);
        if (craftingEvent.isCanceled()) {
            event.getInventory().setResult(null);
            return;
        }
        if (event.getInventory().getViewers().get(0) instanceof Player player) {
            if (!fluentItem.canPlayerUse(player)) {
                event.getInventory().setResult(null);
                return;
            }
        }
        event.getInventory().setResult(itemStack);
    }
}
