package io.github.jwdeveloper.ff.extension.bai.items.impl;

import io.github.jwdeveloper.ff.core.spigot.permissions.implementation.PermissionsUtility;
import io.github.jwdeveloper.ff.core.spigot.player.PlayerUtils;
import io.github.jwdeveloper.ff.extension.bai.common.api.FluentItemBehaviour;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemEvents;
import io.github.jwdeveloper.ff.extension.bai.items.api.mappers.FluentItemStackMapper;
import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemSchema;
import io.github.jwdeveloper.ff.extension.bai.items.impl.decorators.StatsDecorator;
import io.github.jwdeveloper.ff.extension.bai.items.impl.events.FluentItemCreateEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleItem implements FluentItem {

    private final FluentItemSchema fluentItemData;
    private final FluentItemStackMapper mapper;
    private final FluentItemEvents events;
    private final Set<FluentItemBehaviour> behaviours;

    public SimpleItem(FluentItemSchema fluentItemData,
                      FluentItemStackMapper mapper,
                      FluentItemEvents events) {
        this(fluentItemData, mapper, events, new HashSet<>());
    }

    public SimpleItem(FluentItemSchema fluentItemData,
                      FluentItemStackMapper mapper,
                      FluentItemEvents events,
                      Set<FluentItemBehaviour> behaviours) {
        this.fluentItemData = fluentItemData;
        this.mapper = mapper;
        this.events = events;
        this.behaviours = new HashSet<>();
        addBehaviour(behaviours);
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

        var result = mapper.fromItemStack(itemStack);
        if(result.isFailed())
        {
            return false;
        }
        var content = result.getContent();
        return content.getFluentItem().equals(this);
    }

    @Override
    public boolean canPlayerUse(Player player) {
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

    @Override
    public void addBehaviour(FluentItemBehaviour itemBehaviour) {
        behaviours.add(itemBehaviour);
        itemBehaviour.register(this);
    }

    public void removeBehaviour(FluentItemBehaviour itemBehaviour)
    {
        behaviours.remove(itemBehaviour);
        itemBehaviour.unregister(this);
    }
    @Override
    public void addBehaviour(Set<FluentItemBehaviour> behaviours) {
        behaviours.forEach(this::addBehaviour);
    }

}
