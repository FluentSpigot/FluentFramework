package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation;

import io.github.jwdeveloper.ff.extension.gui.FluentInventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.FluentInventoryFactory;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.ButtonWidgetFactory;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.check_box.CheckBoxOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.check_box.CheckboxWidget;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.file_picker.FilePickerOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.file_picker.FilePickerWidget;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.input_chat.ChatInputOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.input_chat.ChatInputWidget;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list.ContentListOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list.ContentListWidget;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.progrss_bar.ProgressBarOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.progrss_bar.ProgressBarWidget;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.listeners.ChatInputListener;

import java.util.function.Consumer;

public class ButtonWidgetFactoryImpl implements ButtonWidgetFactory
{
    private final ChatInputListener chatInputListener;
    private final InventoryApi inventoryApi;

    public ButtonWidgetFactoryImpl(ChatInputListener chatInputListener, InventoryApi inventoryApi) {
        this.chatInputListener = chatInputListener;
        this.inventoryApi = inventoryApi;
    }

    @Override
    public <T> ButtonWidgetFactory contentList(ButtonBuilder builder, Consumer<ContentListOptions<T>> consumer) {
        contentListWidget(builder,consumer);
        return this;
    }

    @Override
    public <T> ContentListWidget<T> contentListWidget(ButtonBuilder builder, Consumer<ContentListOptions<T>> consumer) {
        var options = new ContentListOptions<T>();
        consumer.accept(options);

        var widget = new ContentListWidget<T>(options);
        widget.onCreate(builder, inventoryApi);
        return widget;
    }

    @Override
    public ButtonWidgetFactory checkBox(ButtonBuilder builder, Consumer<CheckBoxOptions> consumer) {
        var options = new CheckBoxOptions();
        consumer.accept(options);

        var widget = new CheckboxWidget(options);
        widget.onCreate(builder, inventoryApi);
        return this;
    }

    @Override
    public ButtonWidgetFactory progressBar(ButtonBuilder builder, Consumer<ProgressBarOptions> consumer) {
        var options = new ProgressBarOptions();
        consumer.accept(options);

        var widget = new ProgressBarWidget(options);
        widget.onCreate(builder, inventoryApi);
        return this;
    }

    @Override
    public ButtonWidgetFactory inputChat(ButtonBuilder builder, Consumer<ChatInputOptions> consumer) {
        var options = new ChatInputOptions();
        consumer.accept(options);
        var widget = new ChatInputWidget(options, chatInputListener);
        widget.onCreate(builder, inventoryApi);
        return this;
    }

    @Override
    public ButtonWidgetFactory filePicker(ButtonBuilder builder, Consumer<FilePickerOptions> consumer) {
        var options = new FilePickerOptions();
        consumer.accept(options);
        var widget = new FilePickerWidget(options);
        widget.onCreate(builder, inventoryApi);
        return this;
    }
}
