package io.github.jwdeveloper.ff.extension.items.api;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.items.impl.events.*;
import lombok.Data;

import java.util.function.Consumer;

@Data
public class FluentItemEvents {

    private final EventGroup<FluentItemUseEvent> onShiftLeftClick = new EventGroup<>();

    private final EventGroup<FluentItemUseEvent> onShiftRightClick = new EventGroup<>();

    private final EventGroup<FluentItemUseEvent> onShiftClick = new EventGroup<>();

    private final EventGroup<FluentItemUseEvent> onClick = new EventGroup<>();

    private final EventGroup<FluentItemUseEvent> onLeftClick = new EventGroup<>();

    private final EventGroup<FluentItemUseEvent> onRightClick = new EventGroup<>();

    private final EventGroup<FluentItemDropEvent> onDrop = new EventGroup<>();

    private final EventGroup<FluentItemPickupEvent> onPickup = new EventGroup<>();

    private final EventGroup<FluentItemSpawnEvent> onSpawn = new EventGroup<>();

    private final EventGroup<FluentItemCreateEvent> onCreating = new EventGroup<>();

    private final EventGroup<FluentItemCraftEvent> onCrafting = new EventGroup<>();

    private final EventGroup<FluentItemConsumeEvent> onEat = new EventGroup<>();

    private final EventGroup<Void> onCraft = new EventGroup<>();


    public void onShiftClick(Consumer<FluentItemUseEvent> eventConsumer)
    {
        onShiftClick.subscribe(eventConsumer);
    }

    public void onShiftLeftClick(Consumer<FluentItemUseEvent> eventConsumer)
    {
        onShiftLeftClick.subscribe(eventConsumer);
    }

    public void onShiftRightClick(Consumer<FluentItemUseEvent> eventConsumer)
    {
        onShiftRightClick.subscribe(eventConsumer);
    }
    public void onClick(Consumer<FluentItemUseEvent> eventConsumer)
    {
        onClick.subscribe(eventConsumer);
    }

    public void onLeftClick(Consumer<FluentItemUseEvent> eventConsumer)
    {
        onLeftClick.subscribe(eventConsumer);
    }

    public void onRightClick(Consumer<FluentItemUseEvent> eventConsumer)
    {
        onRightClick.subscribe(eventConsumer);
    }

    public void onPickup(Consumer<FluentItemPickupEvent> eventConsumer)
    {
        onPickup.subscribe(eventConsumer);
    }

    public void onCreating(Consumer<FluentItemCreateEvent>  o)
    {
        onCreating.subscribe(o);
    }

    public void onCrafting(Consumer<FluentItemCraftEvent>  o)
    {
        onCrafting.subscribe(o);
    }

    public void onSpawn(Consumer<FluentItemSpawnEvent>  o) {
        onSpawn.subscribe(o);
    }

    public void onEat(Consumer<FluentItemConsumeEvent>  o) {
        onEat.subscribe(o);
    }

    public void onDrop(Consumer<FluentItemDropEvent>  o) {
        onDrop.subscribe(o);
    }
}
