package io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.files;

import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.list.ListComponent;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;

public class FilePickerComponent implements InventoryComponent {

    private final String TITLE_TAG = "files";
    @Setter
    private String filesPath;

    @Setter
    private String fileExtension;

    @Override
    public void onInitialization(InventoryDecorator decorator, InventoryApi inventoryApi) {
        var list = decorator.withComponent(new ListComponent<FileModel>());
        list.setContentMapping((data, buttonUI) ->
        {
            buttonUI.setMaterial(Material.PAPER);
            buttonUI.setTitle(data.getFileName());
        });
        list.getTitle().addTitleModel(TITLE_TAG, () -> "loading files...");
        list.setContentSource(() ->
        {
            if (!FileUtility.isPathValid(filesPath)) {
                list.getTitle().setTitleModel(TITLE_TAG, () -> ChatColor.RED + "path not found");
                return new ArrayList<>();
            }
            list.getTitle().setTitleModel(TITLE_TAG, () -> "select file");
            return FileUtility.getFolderFilesName(filesPath, fileExtension)
                    .stream()
                    .map(s -> new FileModel(s, filesPath + FileUtility.separator() + s))
                    .toList();
        });

        list.onContentClick();
    }
}
