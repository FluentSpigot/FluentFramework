package io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation;

import io.github.jwdeveloper.ff.extension.gui.prefab.components.api.InventoryComponentFactory;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.search.SearchComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.title.TitleComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.files.FilePickerComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.list.ListComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.list.data_grid.DataGridComponent;

public class InventoryComponentFactoryImpl implements InventoryComponentFactory {

    @Override
    public <T> ListComponent<T> list() {
        return new ListComponent<>();
    }

    @Override
    public <T> DataGridComponent<T> dataGrid() {
        return new DataGridComponent<>();
    }

    @Override
    public SearchComponent search() {
        return new SearchComponent();
    }

    @Override
    public TitleComponent title() {
        return new TitleComponent();
    }

    @Override
    public FilePickerComponent filePicker() {
        return new FilePickerComponent();
    }
}
