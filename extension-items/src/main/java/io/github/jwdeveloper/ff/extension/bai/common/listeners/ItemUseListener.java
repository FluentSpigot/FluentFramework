package io.github.jwdeveloper.ff.extension.bai.common.listeners;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemApi;
import io.github.jwdeveloper.ff.extension.bai.items.impl.events.FluentItemUseEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Optional;


public class ItemUseListener extends EventBase {
    private final FluentItemApi fluentItemApi;

    public ItemUseListener(Plugin plugin, FluentItemApi fluentItemApi) {
        super(plugin);
        this.fluentItemApi = fluentItemApi;
    }


    @EventHandler
    public void onEntityRightClick(PlayerInteractAtEntityEvent event) {

        var action = event.getPlayer().isSneaking() ? FluentItemUseEvent.Action.SHIFT_RIGHT : FluentItemUseEvent.Action.RIGHT;
        var optional = handleItemUse(event.getPlayer(), event.getPlayer().getItemInUse(), action, event);
        if (optional.isEmpty()) {
            return;
        }
        Bukkit.getPluginManager().callEvent(optional.get());
        event.setCancelled(optional.get().isCancelled());
    }

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
       
        var optional = handleItemUse(event.getPlayer(), event.getItem(), getUseAction(event), event);
        if (optional.isEmpty()) {
            return;
        }

        Bukkit.getPluginManager().callEvent(optional.get());
        event.setCancelled(optional.get().isCancelled());
    }


    private Optional<FluentItemUseEvent> handleItemUse(Player player,
                                                       ItemStack itemStack,
                                                       FluentItemUseEvent.Action action,
                                                       Event event) {
        if (itemStack == null) {
            return Optional.empty();
        }
        var optional = fluentItemApi.fromItemStack(itemStack);
        if (optional.isFailed()) {
            return Optional.empty();
        }
        var fluentItemStack = optional.getObject();
        var fluentItem = fluentItemStack.getFluentItem();
        if (!fluentItem.canPlayerUse(player)) {
            return Optional.empty();
        }
        var fluentItemEvent = new FluentItemUseEvent(player, fluentItemStack, action, event);
        if (action.isShift()) {
            fluentItem.events().getOnShiftClick().invoke(fluentItemEvent);
        } else {
            fluentItem.events().getOnClick().invoke(fluentItemEvent);
        }
        switch (action) {
            case RIGHT -> fluentItem.events().getOnRightClick().invoke(fluentItemEvent);
            case LEFT -> fluentItem.events().getOnLeftClick().invoke(fluentItemEvent);
            case SHIFT_LEFT -> fluentItem.events().getOnShiftLeftClick().invoke(fluentItemEvent);
            case SHIFT_RIGHT -> fluentItem.events().getOnShiftRightClick().invoke(fluentItemEvent);
        }
        return Optional.of(fluentItemEvent);
    }

    public static FluentItemUseEvent.Action getUseAction(PlayerInteractEvent event) {
        var player = event.getPlayer();
        if (player.isSneaking()) {
            switch (event.getAction()) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> {
                    return FluentItemUseEvent.Action.SHIFT_LEFT;
                }
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> {
                    return FluentItemUseEvent.Action.SHIFT_RIGHT;
                }
            }
        } else {
            switch (event.getAction()) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> {
                    return FluentItemUseEvent.Action.LEFT;
                }
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> {
                    return FluentItemUseEvent.Action.RIGHT;
                }
            }
        }
        return FluentItemUseEvent.Action.CLICK;
    }

}