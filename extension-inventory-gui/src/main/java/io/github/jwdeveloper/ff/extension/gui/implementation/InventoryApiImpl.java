package io.github.jwdeveloper.ff.extension.gui.implementation;

import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.extension.gui.api.FluentInventoryFactory;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.api.InventoryComponentFactory;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.InventoryComponentFactoryImpl;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.ButtonWidgetFactory;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.ButtonWidgetFactoryImpl;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.plugin.implementation.listeners.ChatInputListener;

public class InventoryApiImpl implements InventoryApi {

    private final FluentTranslator translator;
    private final FluentTaskFactory taskManager;
    private final ChatInputListener chatInputListener;

    public InventoryApiImpl(FluentTranslator translator, FluentTaskFactory taskManager, ChatInputListener chatInputListener) {
        this.translator = translator;
        this.taskManager = taskManager;
        this.chatInputListener = chatInputListener;
    }

    @Override
    public ButtonWidgetFactory buttons() {
        return new ButtonWidgetFactoryImpl(chatInputListener, this);
    }

    @Override
    public InventoryComponentFactory components() {
        return new InventoryComponentFactoryImpl();
    }

    @Override
    public FluentInventoryFactory inventory() {
        return new FluentInventoryFactoryImpl(this, taskManager);
    }

    @Override
    public FluentTranslator translator() {
        return translator;
    }
}
