package io.github.jwdeveloper.ff.extension.gui.implementation.managers;


import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gui.api.events.*;
import io.github.jwdeveloper.ff.extension.gui.api.managers.EventsManager;

import java.util.function.Consumer;

public class EventManagerImpl implements EventsManager {

    private final EventGroup<GuiCreateEvent> onCreateEvents;
    private final EventGroup<GuiClickEvent> onClick;
    private final EventGroup<GuiClickPlayerInventoryEvent> onClickPlayerInventory;
    private final EventGroup<GuiOpenEvent> onOpen;
    private final EventGroup<GuiOpenEvent> onRefresh;
    private final EventGroup<GuiCloseEvent> onClose;

    private final EventGroup<GuiTickEvent> onTick;

    public EventManagerImpl() {
        this.onCreateEvents = new EventGroup<>();
        this.onClick = new EventGroup<>();
        this.onClickPlayerInventory = new EventGroup<>();
        this.onOpen = new EventGroup<>();
        this.onRefresh = new EventGroup<>();
        this.onClose = new EventGroup<>();
        this.onTick = new EventGroup<>();
    }


    @Override
    public EventGroup<GuiCreateEvent> onCreate() {
        return onCreateEvents;
    }

    @Override
    public void onCreate(Consumer<GuiCreateEvent> event) {
        onCreateEvents.subscribe(event);
    }

    @Override
    public EventGroup<GuiClickEvent> onClick() {
        return onClick;
    }

    @Override
    public void onClick(Consumer<GuiClickEvent> event) {
        onClick.subscribe(event);
    }

    @Override
    public EventGroup<GuiClickPlayerInventoryEvent> onClickPlayerInventory() {
        return onClickPlayerInventory;
    }

    @Override
    public void onClickPlayerInventory(Consumer<GuiClickPlayerInventoryEvent> event) {
        onClickPlayerInventory.subscribe(event);
    }

    @Override
    public EventGroup<GuiOpenEvent> onOpen() {
        return onOpen;
    }

    @Override
    public void onOpen(Consumer<GuiOpenEvent> event) {
        onOpen.subscribe(event);
    }

    @Override
    public EventGroup<GuiOpenEvent> onRefresh() {
        return onRefresh;
    }

    @Override
    public void onRefresh(Consumer<GuiOpenEvent> event) {
        onRefresh.subscribe(event);
    }

    @Override
    public EventGroup<GuiCloseEvent> onClose() {
        return onClose;
    }

    @Override
    public void onClose(Consumer<GuiCloseEvent> event) {
        onClose.subscribe(event);
    }

    @Override
    public EventGroup<GuiTickEvent> onTick() {
        return onTick;
    }

    @Override
    public void onTick(Consumer<GuiTickEvent> event) {
        onTick.subscribe(event);
    }


}
