package io.github.jwdeveloper.ff.extension.gui.core.implementation;

import io.github.jwdeveloper.ff.extension.gui.core.api.FluentInventory;
import io.github.jwdeveloper.ff.plugin.api.features.FluentTranslator;
import lombok.Getter;

public abstract class InventoryComponent implements io.github.jwdeveloper.ff.extension.gui.core.api.InventoryComponent
{
    @Getter
    private final FluentInventory inventory;

    private final FluentTranslator translator;
    @Getter
    private final InventoryFactory factory;

    public InventoryComponent(InventoryFactory factory) {
        this.factory = factory;
        translator = factory.getTranslator();
        inventory = factory.getBasicInventory();
        inventory.events().onCreate().subscribe(createGuiEvent ->
        {
            onCreate(createGuiEvent.getDecorator());
        });
    }


    public String translate(String key)
    {
        return translator.get(key);
    }
}
