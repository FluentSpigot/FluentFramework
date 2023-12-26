package io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.crafting;

import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.BorderComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.ExitButtonComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.title.TitleComponent;

public class CraftingComponent implements InventoryComponent {

    private BorderComponent border;
    private ExitButtonComponent exit;
    private TitleComponent title;

    @Override
    public void onInitialization(InventoryDecorator decorator, InventoryApi inventoryApi) {

        border = decorator.withComponent(new BorderComponent());
        exit = decorator.withComponent(new ExitButtonComponent());
        title = decorator.withComponent(new TitleComponent());

        title.setTitleModel("crafting", () ->
        {
            return "crating schema";
        });
    }


}
