package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.input_chat;

import io.github.jwdeveloper.ff.core.common.java.JavaUtils;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.observer.implementation.ObserverBag;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.api.styles.StyleRenderEvent;
import io.github.jwdeveloper.ff.extension.gui.OLD.events.ButtonClickEvent;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.ButtonWidget;
import io.github.jwdeveloper.ff.plugin.implementation.listeners.ChatInputListener;

public class ChatInputWidget implements ButtonWidget {
    private final ChatInputOptions options;
    private final ChatInputListener inputListener;


    public ChatInputWidget(ChatInputOptions options, ChatInputListener inputListener) {
        this.options = options;
        this.inputListener = inputListener;
    }


    @Override
    public void onCreate(ButtonBuilder builder, InventoryApi inventoryApi) {
        options.valueObserver = JavaUtils.ifNull(options.valueObserver, ObserverBag.createObserver(StringUtils.EMPTY));
        options.openMessage = JavaUtils.ifNull(options.openMessage, "Enter value to chat");
        options.ClickInfo = JavaUtils.ifNull(options.openMessage, "Enter value to chat");
        options.prefix = JavaUtils.ifNull(options.prefix, "Value");

        builder.withStyleRenderer(renderer ->
        {
            renderer.withLeftClickInfo(options.ClickInfo);
            if (options.isCanRender()) {
                renderer.withDescriptionLine(options.getId(), this::onRender);
            }
        });


        if (options.openOnLeftClick) {
            builder.withOnLeftClick(this::onClick);
        } else if (options.openOnRightClick) {
            builder.withOnRightClick(this::onClick);
        }


    }

    public void onClick(ButtonClickEvent event) {
        event.getInventory().close();
        event.getPlayer().sendMessage(options.openMessage);
        inputListener.registerPlayer(event.getPlayer(), chatEvent ->
        {
            options.valueObserver.set(chatEvent.getMessage());
            options.onChatInputEvent.invoke(chatEvent);
            event.getInventory().open(event.getPlayer());
        });
    }

    public String onRender(StyleRenderEvent event) {
        event.builder().field(options.prefix, options.valueObserver.get());
        return event.builder().toString();
    }

}
