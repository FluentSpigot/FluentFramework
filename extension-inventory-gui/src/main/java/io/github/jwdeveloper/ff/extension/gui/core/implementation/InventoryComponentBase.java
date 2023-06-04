package io.github.jwdeveloper.ff.extension.gui.core.implementation;

import io.github.jwdeveloper.ff.extension.gui.core.api.FluentInventory;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import lombok.Setter;

public abstract class InventoryComponentBase implements InventoryComponent
{
    @Setter
    private FluentInventory _inventory;
    @Setter
    private FluentTranslator _translator;

    public FluentInventory inventory()
    {
        if(_inventory == null)
        {
            throw new RuntimeException("Inventory instance is not set");
        }
        return _inventory;
    }

    public FluentTranslator translator()
    {
        inventory();
        return _translator;
    }

}
