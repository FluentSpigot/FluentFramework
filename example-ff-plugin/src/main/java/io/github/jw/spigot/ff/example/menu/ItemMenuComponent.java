package io.github.jw.spigot.ff.example.menu;

import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import org.bukkit.inventory.ItemStack;

public class ItemMenuComponent implements InventoryComponent {
    private FluentItemApi fluentItemApi;

    public ItemMenuComponent(FluentItemApi fluentItemApi) {
        this.fluentItemApi = fluentItemApi;
    }

    @Override
    public void onInitialization(InventoryDecorator decorator, InventoryApi inventoryApi) {

        var listComponent = inventoryApi.components().<FluentItem>list();
        listComponent.setContentSource(() -> fluentItemApi.findItems());
        listComponent.setContentMapping((data, buttonUI) ->
        {
            var itemStack = data.toItemStack();
            buttonUI.fromItemStack(itemStack);
        });

        listComponent.onContentClick(guiClickEvent ->
        {
            var content = (FluentItem) guiClickEvent.getButton().getDataContext();
            content.give(guiClickEvent.getPlayer());
        });

        decorator.withComponent(listComponent);
    }
}
