package io.github.jwdeveloper.ff.extension.gui.inventory.components.pagination;

import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.inventory.IPaginationa;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;

public class PaginationComponent<T> implements IPaginationa, InventoryComponent {


    @Override
    public void onCreate(InventoryDecorator decorator) {

    }

    @Override
    public void openPage(int number) {

    }

    public interface ButtonMapping<T>
    {
         void onMap(T item, ButtonUI buttonUI);
    }
}
