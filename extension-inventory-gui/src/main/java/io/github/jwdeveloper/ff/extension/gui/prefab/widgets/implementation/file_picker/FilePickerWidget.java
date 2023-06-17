package io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.file_picker;

import io.github.jwdeveloper.ff.extension.gui.OLD.events.ButtonClickEvent;
import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.api.FluentInventoryFactory;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.files.FilePickerComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.ButtonWidget;
import org.bukkit.Material;

public class FilePickerWidget implements ButtonWidget {
    private final FilePickerOptions options;
    private FluentInventory filePicker;

    public FilePickerWidget(FilePickerOptions options) {
        this.options = options;

    }

    @Override
    public void onCreate(ButtonBuilder builder, InventoryApi inventoryApi) {
        builder.withStyleRenderer(styleRendererOptionsDecorator ->
        {
            styleRendererOptionsDecorator.withTitle("File Picker");
            styleRendererOptionsDecorator.withLeftClickInfo("Click to select a file");
        });
        builder.withMaterial(Material.WRITABLE_BOOK);
        builder.withOnLeftClick((e) -> onClick(e, inventoryApi.inventory()));
    }


    public void onClick(ButtonClickEvent event, FluentInventoryFactory factory) {
        if (filePicker == null) {

            var picker = new FilePickerComponent();
            picker.setFilesPath(options.directoryPath);
            picker.setFileExtension(options.filesExtensions);

            filePicker = factory.create(picker);
            filePicker.setParent(event.getInventory());
        }

        filePicker.open(event.getPlayer());
    }
}
