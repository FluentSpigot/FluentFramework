package io.github.jwdeveloper.ff.extension.gui.inventory.components.list;

import io.github.jwdeveloper.ff.extension.gui.core.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.inventory.InventoryComponentOld;
import io.github.jwdeveloper.ff.extension.gui.inventory.components.pagination.PaginationBuilder;
import io.github.jwdeveloper.ff.extension.gui.inventory.components.back.BackComponent;
import io.github.jwdeveloper.ff.extension.gui.inventory.components.search.SearchBuilder;

public class ListComponent<T> extends InventoryComponentOld
{
    PaginationBuilder<T> paginationBuilder;
    SearchBuilder searchBuilde;
    public ListComponent(PaginationBuilder<T> paginationBuilder,
                         SearchBuilder searchBuilder)
    {

    }


    @Override
    public void onInitialize(InventoryDecorator decorator)
    {
        decorator.withComponent(paginationBuilder.build());
      //  decorator.withComponent(searchBuilde.build());
        decorator.withComponent(new BackComponent());
    }
}
