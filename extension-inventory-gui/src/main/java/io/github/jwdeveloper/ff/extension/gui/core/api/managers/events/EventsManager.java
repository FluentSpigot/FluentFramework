package io.github.jwdeveloper.ff.extension.gui.core.api.managers.events;

import java.util.function.Consumer;

public interface EventsManager
{
     EventsGroup<CreateGuiEvent> onCreate();
     void onCreate(Consumer<CreateGuiEvent> event);

     EventsGroup<ClickEvent> onClick();
     void onClick(Consumer<ClickEvent> event);

     EventsGroup<ClickPlayerInventoryEvent> onClickPlayerInventory();

     void onClickPlayerInventory(Consumer<ClickPlayerInventoryEvent> event);

     EventsGroup<OpenGuiEvent> onOpen();
     void onOpen(Consumer<OpenGuiEvent> event);

     EventsGroup<OpenGuiEvent> onClose();

     void onClose(Consumer<OpenGuiEvent> event);

     EventsGroup onDrag();
}
