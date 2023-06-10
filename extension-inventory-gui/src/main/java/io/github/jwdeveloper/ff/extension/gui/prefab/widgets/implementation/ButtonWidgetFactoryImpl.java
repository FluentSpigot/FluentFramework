package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation;

import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.ButtonWidgetFactory;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.check_box.CheckBoxOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.check_box.CheckboxWidget;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.input_chat.ChatInputOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.input_chat.ChatInputWidget;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list.ContentListOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list.ContentListWidget;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.progrss_bar.ProgressBarOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.progrss_bar.ProgressBarWidget;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.listeners.ChatInputListener;

import java.util.function.Consumer;

public class ButtonWidgetFactoryImpl implements ButtonWidgetFactory {
    @Override
    public <T> ButtonWidgetFactory contentList(ButtonBuilder builder, Consumer<ContentListOptions<T>> consumer) {
        var options = new ContentListOptions<T>();
        consumer.accept(options);

        var widget = new ContentListWidget<T>(options);
        widget.onCreate(builder);
        return this;
    }

    @Override
    public ButtonWidgetFactory checkBox(ButtonBuilder builder, Consumer<CheckBoxOptions> consumer) {
        var options = new CheckBoxOptions();
        consumer.accept(options);

        var widget = new CheckboxWidget(options);
        widget.onCreate(builder);
        return this;
    }

    @Override
    public ButtonWidgetFactory progressBar(ButtonBuilder builder, Consumer<ProgressBarOptions> consumer) {
        var options = new ProgressBarOptions();
        consumer.accept(options);

        var widget = new ProgressBarWidget(options);
        widget.onCreate(builder);
        return this;
    }

    @Override
    public ButtonWidgetFactory inputChat(ButtonBuilder builder, Consumer<ChatInputOptions> consumer) {
        var options = new ChatInputOptions();
        consumer.accept(options);

        var listener = FluentApi.container().findInjection(ChatInputListener.class);

        var widget = new ChatInputWidget(options, listener);
        widget.onCreate(builder);
        return this;
    }
}
