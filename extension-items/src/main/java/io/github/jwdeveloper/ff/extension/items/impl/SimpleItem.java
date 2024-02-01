package io.github.jwdeveloper.ff.extension.items.impl;

import io.github.jwdeveloper.ff.core.spigot.permissions.implementation.PermissionsUtility;
import io.github.jwdeveloper.ff.core.spigot.player.PlayerUtils;
import io.github.jwdeveloper.ff.extension.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.items.api.FluentItemEvents;
import io.github.jwdeveloper.ff.extension.items.api.mappers.FluentItemStackMapper;
import io.github.jwdeveloper.ff.extension.items.api.schema.FluentItemSchema;
import io.github.jwdeveloper.ff.extension.items.spigot.decorators.StatsDecorator;
import io.github.jwdeveloper.ff.extension.items.impl.events.FluentItemCreateEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SimpleItem implements FluentItem {

    private final FluentItemSchema fluentItemData;
    private final FluentItemStackMapper mapper;
    private final FluentItemEvents events;

    public SimpleItem(FluentItemSchema fluentItemData,
                      FluentItemStackMapper mapper,
                      FluentItemEvents events) {
        this.fluentItemData = fluentItemData;
        this.mapper = mapper;
        this.events = events;
    }


    @Override
    public ItemStack toItemStack() {
        var itemStack = mapper.toItemStack(this.getSchema());
        var createEvent = new FluentItemCreateEvent(this, itemStack);
        events.getOnCreating().invoke(createEvent);
        new StatsDecorator().onDecorating(createEvent);
        return itemStack;
    }

    @Override
    public boolean isItemStack(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        if (!itemStack.hasItemMeta()) {
            return false;
        }

        return itemStack.getItemMeta().getDisplayName().equals(getSchema().getDisplayName());
    }

    @Override
    public boolean isPlayerCanUse(Player player) {
        return PermissionsUtility.hasOnePermission(player, getSchema().getPermission());
    }

    @Override
    public ItemStack give(Player... players) {
        var item = toItemStack();
        for (var player : players) {
            PlayerUtils.giveItem(player, item, player1 ->
            {
                player1.getLocation().getWorld().dropItem(player1.getLocation(), item);
            });
        }
        return item;
    }

    @Override
    public FluentItemEvents events() {
        return events;
    }

    @Override
    public ItemStack drop(Location location) {
        var itemStack = toItemStack();
        location.getWorld().dropItem(location, itemStack);
        return itemStack;
    }

    @Override
    public FluentItemSchema getSchema() {
        return fluentItemData;
    }

}
