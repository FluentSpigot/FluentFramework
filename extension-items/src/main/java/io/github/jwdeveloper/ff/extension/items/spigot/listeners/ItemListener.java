package io.github.jwdeveloper.ff.extension.items.spigot.listeners;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import io.github.jwdeveloper.ff.extension.items.api.FluentItemApi;
import io.github.jwdeveloper.ff.extension.items.impl.events.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;

public class ItemListener extends EventBase {
    private final FluentItemApi fluentItemApi;

    public ItemListener(Plugin plugin, FluentItemApi fluentItemApi) {
        super(plugin);
        this.fluentItemApi = fluentItemApi;
    }


    @EventHandler
    public void onPickupEvent(EntityPickupItemEvent event) {
        var itemStack = event.getItem().getItemStack();
        var optional = fluentItemApi.fromItemStack(itemStack);
        if (optional.isFailed()) {
            return;
        }
        var fluentItemStack = optional.getContent();
        var fluentItem = fluentItemStack.getFluentItem();
        if (event.getEntity() instanceof Player player) {
            if (!fluentItem.isPlayerCanUse(player)) {
                event.setCancelled(true);
                return;
            }
        }
        var fluentItemEvent = new FluentItemPickupEvent(fluentItem, itemStack, event.getEntity());
        fluentItem.events().getOnPickup().invoke(fluentItemEvent);
        event.setCancelled(fluentItemEvent.isCancelled());
    }


    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        var itemstack = event.getItemDrop().getItemStack();
        var optional = fluentItemApi.fromItemStack(itemstack);
        if (optional.isFailed()) {
            return;
        }
        var fluentItemStack = optional.getContent();
        var player = event.getPlayer();
        var fluentItemEvent = new FluentItemDropEvent(player, fluentItemStack);
        fluentItemStack.getFluentItem().events().getOnDrop().invoke(fluentItemEvent);
        event.setCancelled(fluentItemEvent.isCancelled());
    }

    @EventHandler
    public void onItemSpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof Item)) {
            return;
        }
        var itemStack = ((Item) event.getEntity()).getItemStack();
        var optional = fluentItemApi.fromItemStack(itemStack);
        if (optional.isFailed()) {
            return;
        }
        var fluentItemStack = optional.getContent();
        var fluentItem = fluentItemStack.getFluentItem();
        var fluentItemEvent = new FluentItemSpawnEvent(fluentItem, itemStack);
        fluentItem.events().getOnSpawn().invoke(fluentItemEvent);
        event.setCancelled(fluentItemEvent.isCancelled());
    }

    @EventHandler
    public void OnItemConsume(PlayerItemConsumeEvent event) {
        var itemStack = event.getItem();
        var optional = fluentItemApi.fromItemStack(itemStack);
        if (optional.isFailed()) {
            return;
        }
        var fluentItemStack = optional.getContent();
        var fluentItem = fluentItemStack.getFluentItem();
        var player = event.getPlayer();
        if (!fluentItem.isPlayerCanUse(player)) {
            return;
        }
        var fluentItemEvent = new FluentItemConsumeEvent(event.getPlayer(), fluentItem);
        fluentItem.events().getOnEat().invoke(fluentItemEvent);
        event.setCancelled(fluentItemEvent.isCancelled());
    }
}
