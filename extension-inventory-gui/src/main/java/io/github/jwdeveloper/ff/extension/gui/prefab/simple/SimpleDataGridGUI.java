package io.github.jwdeveloper.ff.extension.gui.prefab.simple;

import io.github.jwdeveloper.ff.extension.gui.OLD.events.ButtonClickEvent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.pagination.ButtonMapping;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.list.data_grid.DataGridAction;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.list.data_grid.DataGridComponent;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.function.Consumer;

public abstract class SimpleDataGridGUI<T> extends SimpleGUI {
    private DataGridComponent<T> dataGrid;

    @Override
    public void onInitialization(InventoryDecorator decorator, InventoryApi inventoryApi) {
        dataGrid = decorator.withComponent(inventoryApi.components().<T>dataGrid());
        dataGrid.setContentSource(this::getContentSource);
        dataGrid.setContentMapping(getContentMapping());
        super.onInitialization(decorator, inventoryApi);
    }

    public abstract ButtonMapping<T> getContentMapping();

    public abstract List<T> getContentSource();

    @Override
    public abstract void onInit(InventoryDecorator decorator, InventoryApi inventoryApi);

    public void onCreate(Consumer<ButtonClickEvent> consumer) {
        dataGrid.onDataGridAction(DataGridAction.CREATE, consumer);
    }

    public void onDelete(Consumer<ButtonClickEvent> consumer) {
        dataGrid.onDataGridAction(DataGridAction.DELETE, consumer);
    }

    public void onSelect(Consumer<ButtonClickEvent> consumer) {
        dataGrid.onDataGridAction(DataGridAction.SELECT, consumer);
    }

    public void errorTitle(String title) {
        dataGrid.getTitle().setTitleModel("error", () -> ChatColor.RED+title);
    }
    public void title(String title)
    {
        dataGrid.getTitle().setTitleModel(DataGridComponent.TITLE_TAG, () -> title);
    }
}
