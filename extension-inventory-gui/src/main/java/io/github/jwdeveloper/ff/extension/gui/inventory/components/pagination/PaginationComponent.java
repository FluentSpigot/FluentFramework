package io.github.jwdeveloper.ff.extension.gui.inventory.components.pagination;

import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.inventory.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.inventory.IPaginationa;
import new_version.implementation.buttons.ButtonUI;

public class PaginationComponent<T> extends InventoryComponent implements IPaginationa
{

    @Override
    public void onInitialize(InventoryDecorator inventoryDecorator)
    {

    }


    @Override
    public void openPage(int number) {

    }

    public interface ButtonMapping<T>
    {
         void onMap(T item, ButtonUI buttonUI);
    }
}
