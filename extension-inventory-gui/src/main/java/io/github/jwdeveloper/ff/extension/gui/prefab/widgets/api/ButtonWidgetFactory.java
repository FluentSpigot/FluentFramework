package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api;

import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.check_box.CheckBoxOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.file_picker.FilePickerOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.input_chat.ChatInputOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list.ContentListOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list.ContentListWidget;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list_check_box.ContentListCheckBoxOptions;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.progrss_bar.ProgressBarOptions;

import java.util.function.Consumer;

public interface ButtonWidgetFactory {
    <T> ButtonWidgetFactory contentList(ButtonBuilder builder, Consumer<ContentListOptions<T>> consumer);
     <T> ContentListWidget<T> contentListWidget(ButtonBuilder builder, Consumer<ContentListOptions<T>> consumer);
    ButtonWidgetFactory checkBoxList(ButtonBuilder builder, Consumer<ContentListCheckBoxOptions> consumer);
    ButtonWidgetFactory checkBox(ButtonBuilder builder, Consumer<CheckBoxOptions> consumer);
    ButtonWidgetFactory progressBar(ButtonBuilder builder, Consumer<ProgressBarOptions> consumer);
    ButtonWidgetFactory inputChat(ButtonBuilder builder, Consumer<ChatInputOptions> consumer);
    ButtonWidgetFactory filePicker(ButtonBuilder builder, Consumer<FilePickerOptions> consumer);
}
