package io.github.jwdeveloper.ff.extension.gui.implementation.managers;


import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gui.api.events.*;
import io.github.jwdeveloper.ff.extension.gui.api.managers.EventsManager;

import java.util.function.Consumer;

public class EventManagerImpl implements EventsManager {

    private final EventGroup<CreateGuiEvent> onCreateEvents;
    private final EventGroup<ClickGuiEvent> onClick;
    private final EventGroup<ClickPlayerInventoryEvent> onClickPlayerInventory;
    private final EventGroup<OpenGuiEvent> onOpen;
    private final EventGroup<OpenGuiEvent> onRefresh;
    private final EventGroup<CloseGuiEvent> onClose;

    private final EventGroup<TickGuiEvent> onTick;

    public EventManagerImpl()
    {
        this.onCreateEvents = new EventGroup<>();
        this.onClick = new EventGroup<>();
        this.onClickPlayerInventory = new EventGroup<>();
        this.onOpen = new EventGroup<>();
        this.onRefresh = new EventGroup<>();
        this.onClose = new EventGroup<>();
        this.onTick = new EventGroup<>();
    }


    @Override
    public EventGroup<CreateGuiEvent> onCreate() {
        return onCreateEvents;
    }

    @Override
    public void onCreate(Consumer<CreateGuiEvent> event) {
        onCreateEvents.subscribe(event);
    }

    @Override
    public EventGroup<ClickGuiEvent> onClick() {
        return onClick;
    }

    @Override
    public void onClick(Consumer<ClickGuiEvent> event) {
       onClick.subscribe(event);
    }

    @Override
    public EventGroup<ClickPlayerInventoryEvent> onClickPlayerInventory() {
        return onClickPlayerInventory;
    }

    @Override
    public void onClickPlayerInventory(Consumer<ClickPlayerInventoryEvent> event) {
        onClickPlayerInventory.subscribe(event);
    }

    @Override
    public EventGroup<OpenGuiEvent> onOpen() {
        return onOpen;
    }

    @Override
    public void onOpen(Consumer<OpenGuiEvent> event) {
        onOpen.subscribe(event);
    }

    @Override
    public EventGroup<OpenGuiEvent> onRefresh() {
        return onRefresh;
    }

    @Override
    public void onRefresh(Consumer<OpenGuiEvent> event) {
        onRefresh.subscribe(event);
    }

    @Override
    public EventGroup<CloseGuiEvent> onClose() {
        return onClose;
    }

    @Override
    public void onClose(Consumer<CloseGuiEvent> event) {
        onClose.subscribe(event);
    }

    @Override
    public EventGroup<TickGuiEvent> onTick() {
        return onTick;
    }

    @Override
    public void onTick(Consumer<TickGuiEvent> event) {
        onTick.subscribe(event);
    }


}
