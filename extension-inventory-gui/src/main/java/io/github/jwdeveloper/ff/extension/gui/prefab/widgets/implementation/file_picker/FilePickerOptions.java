package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.file_picker;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.files.FileModel;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.WidgetOptions;
import lombok.Setter;

import java.util.function.Consumer;

public class FilePickerOptions extends WidgetOptions {
    @Setter
    String directoryPath;

    @Setter
    String filesExtensions;

    EventGroup<FileModel> fileSelectedEvent = new EventGroup<>();

    public void onFileSelected(Consumer<FileModel> consumer)
    {
        fileSelectedEvent.subscribe(consumer);
    }
}
