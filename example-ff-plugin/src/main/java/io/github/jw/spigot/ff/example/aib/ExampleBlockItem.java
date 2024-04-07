package io.github.jw.spigot.ff.example.aib;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockApi;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemApi;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;


public class ExampleBlockItem {


    public void register() {
        var items = FluentApi.container().findInjection(FluentItemApi.class);
        var blocks = FluentApi.container().findInjection(FluentBlockApi.class);
        var customDiamond = items.addItem()
                .withSchema(schema ->
                {
                    schema.withMaterial(Material.DIAMOND);
                    schema.withCustomModelId(513);
                    schema.withName("Custom Diamond");
                    schema.withDisplayName("Custom diamond block");
                    schema.withTag("custom-diamond");
                })
                .makeBlock()
                .buildAndRegister();

        FluentApi.createCommand("diamond-test")
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onPlayerExecute(playerCommandEvent ->
                    {

                        customDiamond.give(playerCommandEvent.getPlayer());
                    });
                })
                .buildAndRegister();


    }
}
