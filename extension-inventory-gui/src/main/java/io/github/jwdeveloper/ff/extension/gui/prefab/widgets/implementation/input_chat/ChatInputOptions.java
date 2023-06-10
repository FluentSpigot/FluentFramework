package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.input_chat;

import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.WidgetOptions;
import lombok.Setter;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.function.Consumer;


public class ChatInputOptions extends WidgetOptions {
    @Setter
    String openMessage;

    @Setter
    String prefix;

    @Setter
    String ClickInfo;

    @Setter
    Observer<String> valueObserver;

    @Setter
    boolean openOnLeftClick;

    @Setter
    boolean openOnRightClick;

    EventGroup<AsyncPlayerChatEvent> onChatInputEvent = new EventGroup<>();

    public void onChatInput(Consumer<AsyncPlayerChatEvent> event) {
        onChatInputEvent.subscribe(event);
    }

}
