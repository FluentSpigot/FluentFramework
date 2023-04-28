package io.github.jwdeveloper.ff.extension.gui.core.implementation.managers;


import io.github.jwdeveloper.ff.extension.gui.core.api.managers.events.*;

import java.util.function.Consumer;

public class EventManagerImpl implements EventsManager {


    private final EventsGroup<CreateGuiEvent> onCreateEvents;
    private final EventsGroup<ClickEvent> onClick;
    private final EventsGroup<ClickPlayerInventoryEvent> onClickPlayerInventory;
    private final EventsGroup<OpenGuiEvent> onOpen;

    private final EventsGroup<?> onClose;

    private final EventsGroup<?> onDrag;

    public EventManagerImpl()
    {
        this.onCreateEvents = new EventsGroup<>();
        this.onClick = new EventsGroup<>();
        this.onClickPlayerInventory = new EventsGroup<>();
        this.onOpen = new EventsGroup<>();
        this.onClose = new EventsGroup<>();
        this.onDrag = new EventsGroup<>();
    }


    @Override
    public EventsGroup<CreateGuiEvent> onCreate() {
        return onCreateEvents;
    }

    @Override
    public void onCreate(Consumer<CreateGuiEvent> event) {
        onCreateEvents.subscribe(event);
    }

    @Override
    public EventsGroup<ClickEvent> onClick() {
        return onClick;
    }

    @Override
    public void onClick(Consumer<ClickEvent> event) {
       onClick.subscribe(event);
    }

    @Override
    public EventsGroup<ClickPlayerInventoryEvent> onClickPlayerInventory() {
        return onClickPlayerInventory;
    }

    @Override
    public void onClickPlayerInventory(Consumer<ClickPlayerInventoryEvent> event) {
        onClickPlayerInventory.subscribe(event);
    }

    @Override
    public EventsGroup<OpenGuiEvent> onOpen() {
        return onOpen;
    }

    @Override
    public void onOpen(Consumer<OpenGuiEvent> event) {
        onOpen.subscribe(event);
    }

    @Override
    public EventsGroup onClose() {
        return onClose;
    }

    @Override
    public void onClose(Consumer<OpenGuiEvent> event) {
        //onClose.subscribe(event);
    }

    @Override
    public EventsGroup onDrag() {
        return onDrag;
    }
}
