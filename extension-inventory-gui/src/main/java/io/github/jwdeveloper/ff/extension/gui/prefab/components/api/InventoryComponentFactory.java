package io.github.jwdeveloper.ff.extension.gui.prefab.components.api;

import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.search.SearchComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.title.TitleComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.files.FilePickerComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.list.ListComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.list.data_grid.DataGridComponent;

public interface InventoryComponentFactory
{
    <T> ListComponent<T> list();
    <T> DataGridComponent<T> dataGrid();

    SearchComponent search();

    TitleComponent title();

    FilePickerComponent filePicker();
}
