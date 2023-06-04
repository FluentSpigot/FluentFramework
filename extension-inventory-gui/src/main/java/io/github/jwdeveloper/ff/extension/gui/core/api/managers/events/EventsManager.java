package io.github.jwdeveloper.ff.extension.gui.core.api.managers.events;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;

import java.util.function.Consumer;

public interface EventsManager
{
     EventGroup<CreateGuiEvent> onCreate();
     void onCreate(Consumer<CreateGuiEvent> event);

     EventGroup<ClickGuiEvent> onClick();
     void onClick(Consumer<ClickGuiEvent> event);

     EventGroup<ClickPlayerInventoryEvent> onClickPlayerInventory();

     void onClickPlayerInventory(Consumer<ClickPlayerInventoryEvent> event);

     EventGroup<OpenGuiEvent> onOpen();
     void onOpen(Consumer<OpenGuiEvent> event);

     EventGroup<CloseGuiEvent> onClose();

     void onClose(Consumer<CloseGuiEvent> event);

     EventGroup onDrag();
}
