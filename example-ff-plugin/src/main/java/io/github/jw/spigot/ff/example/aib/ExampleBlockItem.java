package io.github.jw.spigot.ff.example.aib;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


public class ExampleBlockItem {


    public void register() {
        var items = FluentApi.container().findInjection(FluentItemApi.class);
        var inventory = FluentApi.container().findInjection(InventoryApi.class);

        var components = inventory.components();
        var inv = inventory.inventory().create(components.list(), components.filePicker(), components.dataGrid());
        var customDiamond = items.addItem()
                .withSchema(schema ->
                {
                    schema.withMaterial(Material.PRISMARINE_SHARD);
                    schema.withCustomModelId(513);
                    schema.withName("Custom Diamond");
                    schema.withDisplayName("Custom diamond block");
                    schema.withTag("custom-diamond");
                    schema.withStackable(false);
                })
                .makeBlock(block ->
                {
                    block.withDrop()
                            .addDefaultDrop(new ItemStack(Material.APPLE))
                            .addDrop(new ItemStack(Material.DIAMOND), 0.25f);

                    block.withStates()
                            .addState("open", 12, Material.DIAMOND_PICKAXE)
                            .addState("close", 12, Material.GOLDEN_PICKAXE);

                    block.withEvents()
                            .onRightClick(event ->
                            {
                                inv.open(event.getPlayer());
                            })
                            .onState(event ->
                            {
                                FluentLogger.LOGGER.info(event.getState().getName());
                            })
                            .onLeftClick(event ->
                            {

                        /*        var currentState = event.getFluentBlockInstance().getState();
                                if(currentState.getName().equals("open"))
                                {
                                    event.getFluentBlockInstance().setState("close");
                                }
                                else
                                {
                                    event.getFluentBlockInstance().setState("open");
                                }*/


                            });
                })
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
