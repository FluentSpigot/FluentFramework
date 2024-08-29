package io.github.jw.spigot.ff.example.menu;

import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

public class ItemMenuExtension implements FluentApiExtension {

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        builder.container().registerTransient(ItemMenuComponent.class);
    }


    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

        var inventoryFramework = fluentAPI.container().findInjection(InventoryApi.class);
        var invComponent = fluentAPI.container().findInjection(ItemMenuComponent.class);
        var inv = inventoryFramework.inventory().create(invComponent);
        fluentAPI.createCommand("item-menu")
                .onPlayerExecute(playerCommandEvent ->
                {
                    inv.open(playerCommandEvent.sender());
                }).register();
    }
}
