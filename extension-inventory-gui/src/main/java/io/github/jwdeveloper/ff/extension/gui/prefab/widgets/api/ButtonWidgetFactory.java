package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api;

import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.check_box.CheckBoxOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.input_chat.ChatInputOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list.ContentListOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.progrss_bar.ProgressBarOptions;

import java.util.function.Consumer;

public interface ButtonWidgetFactory {
    <T> ButtonWidgetFactory contentList(ButtonBuilder builder, Consumer<ContentListOptions<T>> consumer);
    ButtonWidgetFactory checkBox(ButtonBuilder builder, Consumer<CheckBoxOptions> consumer);
    ButtonWidgetFactory progressBar(ButtonBuilder builder, Consumer<ProgressBarOptions> consumer);
    ButtonWidgetFactory inputChat(ButtonBuilder builder, Consumer<ChatInputOptions> consumer);
}
