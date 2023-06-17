/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package io.github.jwdeveloper.ff.color_picker.implementation.gui;

import io.github.jwdeveloper.ff.color_picker.api.ColorInfo;
import io.github.jwdeveloper.ff.color_picker.implementation.ColorPicker;
import io.github.jwdeveloper.ff.color_picker.implementation.ColorsService;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import io.github.jwdeveloper.ff.extension.gui.OLD.events.ButtonClickEvent;
import io.github.jwdeveloper.ff.extension.gui.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.list.data_grid.DataGridAction;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.list.data_grid.DataGridComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.ButtonWidget;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;


public class ColorPickerWidget implements ButtonWidget {
    private final ColorPicker colorPicker;
    private final ColorsService colorsService;
    private final FluentTranslator translator;
    private final EventGroup<ColorInfo> colorSelectedEvent;
    private FluentInventory colorPickerInventory;

    @Setter
    private ItemStack displayedItem;

    public ColorPickerWidget(ColorPicker colorPicker, ColorsService colorsService, FluentTranslator translator) {
        this.colorPicker = colorPicker;
        this.colorsService = colorsService;
        this.translator = translator;
        colorSelectedEvent = new EventGroup<>();
    }

    @Override
    public void onCreate(ButtonBuilder builder, InventoryApi api) {
        builder.withStyleRenderer(options ->
        {
            options.withRightClickInfo("Select Color");
        });
        builder.withOnLeftClick(buttonClickEvent ->
        {
            var picker = getColorPicker(api);
            picker.setParent(buttonClickEvent.getInventory());
            picker.open(buttonClickEvent.getPlayer());
        });
    }

    private FluentInventory getColorPicker(InventoryApi api) {
        if (colorPickerInventory != null) {
            return colorPickerInventory;
        }

        if (displayedItem == null) {
            throw new RuntimeException("Displayed item in color picker must not be null");
        }
        var dataGrid = api.components().<ColorInfo>dataGrid();
        colorPickerInventory = api.inventory().create(dataGrid);
        colorPickerInventory.events().onCreate(guiCreateEvent ->
        {
            dataGrid.getTitle().setTitleModel(DataGridComponent.TITLE_TAG, () -> api.translator().get("gui.color-picker.title"));
            dataGrid.setContentSource(colorsService::getColors);
            dataGrid.setContentMapping(this::contentMapping);
            dataGrid.getSearch().<ColorInfo>addSearchFilter(
                    translator.get("gui.base.search.filters.by-name"),
                    (dataSource, query) -> dataSource.stream().filter(e -> e.getName().contains(query)).toList());

            dataGrid.onDataGridAction(DataGridAction.DELETE, this::handleDelete);
            dataGrid.onDataGridAction(DataGridAction.CREATE, this::handleCreate);
            dataGrid.onDataGridAction(DataGridAction.SELECT, this::handleSelect);


        });
        return colorPickerInventory;
    }

    private void contentMapping(ColorInfo data, ButtonUI button) {
        button.editStyleRendererOptions(options ->
        {
            options.withTitle(data.getName());
            options.withLeftClickInfo(translator.get("gui.base.select"));
        });
        button.setCustomMaterial(displayedItem.getType(), displayedItem.getItemMeta().getCustomModelData());
        button.setMaterialColor(data.getColor());
    }

    private void handleDelete(ButtonClickEvent event) {
        colorsService.remove(event.getButton().getDataContext());
    }

    private void handleCreate(ButtonClickEvent event) {
        event.getInventory().close();
        colorPicker.register(event.getPlayer(), output ->
        {
            colorsService.insert(new ColorInfo(output.getAsHex(), output.getAsBukkitColor(), false));
            event.getInventory().open(event.getPlayer());
        });
    }

    private void handleSelect(ButtonClickEvent event) {
        colorSelectedEvent.invoke(event.getButton().getDataContext());
    }

    private void createButtonDecorator(ButtonBuilder builder) {
        builder.withStyleRenderer(renderer ->
        {
            renderer.withTitle(translator.get("gui.color-picker.add-color.title"));
            renderer.withDescription(translator.get("gui.color-picker.add-color.desc-1", "#FFFFFF"));
            renderer.withLeftClickInfo(translator.get("gui.color-picker.add-color.click-left"));
        });
    }

}
