package io.github.jwdeveloper.ff.extension.gui.api;

import io.github.jwdeveloper.ff.extension.gui.prefab.components.api.InventoryComponentFactory;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.ButtonWidgetFactory;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;

public interface InventoryApi
{
    ButtonWidgetFactory buttonWidgets();

    InventoryComponentFactory inventoryComponents();

    FluentTranslator translator();
}
