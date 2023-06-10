package io.github.jwdeveloper.ff.extension.gui.implementation;

import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.api.InventoryComponentFactory;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.api.ButtonWidgetFactory;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;

public class InventoryApiImpl implements InventoryApi
{

    private final FluentTranslator translator;

    private final ButtonWidgetFactory factory;

    private final InventoryComponentFactory inventoryComponents;

    public InventoryApiImpl(FluentTranslator translator, ButtonWidgetFactory buttonWidgetFactory, InventoryComponentFactory inventoryComponentFactory) {
        this.translator = translator;
        this.inventoryComponents = inventoryComponentFactory;
        factory = buttonWidgetFactory;
    }

    @Override
    public ButtonWidgetFactory buttonWidgets() {
        return factory;
    }

    @Override
    public InventoryComponentFactory inventoryComponents() {
        return inventoryComponents;
    }

    @Override
    public FluentTranslator translator() {
        return translator;
    }
}
