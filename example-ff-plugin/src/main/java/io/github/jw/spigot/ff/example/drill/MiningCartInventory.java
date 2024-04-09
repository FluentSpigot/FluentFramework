package io.github.jw.spigot.ff.example.drill;

import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryComponent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import org.bukkit.ChatColor;

public class MiningCartInventory implements InventoryComponent {

    @Override
    public void onInitialization(InventoryDecorator decorator, InventoryApi inventoryApi) {


        var title = inventoryApi.components().title();


        var moveLeft = "\uF836";
        title.addTitleModel("title", () ->
        {
            return ChatColor.WHITE + moveLeft + "\uE452";
        });
        decorator.withComponent(title);


    }


}
