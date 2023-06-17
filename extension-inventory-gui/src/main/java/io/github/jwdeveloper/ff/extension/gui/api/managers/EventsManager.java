package io.github.jwdeveloper.ff.extension.gui.api.managers;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gui.api.events.*;

import java.util.function.Consumer;

public interface EventsManager {
    EventGroup<GuiCreateEvent> onCreate();

    void onCreate(Consumer<GuiCreateEvent> event);

    EventGroup<GuiClickEvent> onClick();

    void onClick(Consumer<GuiClickEvent> event);

    EventGroup<GuiClickPlayerInventoryEvent> onClickPlayerInventory();

    void onClickPlayerInventory(Consumer<GuiClickPlayerInventoryEvent> event);

    EventGroup<GuiOpenEvent> onOpen();

    void onOpen(Consumer<GuiOpenEvent> event);

    EventGroup<GuiOpenEvent> onRefresh();

    void onRefresh(Consumer<GuiOpenEvent> event);

    EventGroup<GuiCloseEvent> onClose();

    void onClose(Consumer<GuiCloseEvent> event);

    EventGroup<GuiTickEvent> onTick();

    void onTick(Consumer<GuiTickEvent> event);
}
