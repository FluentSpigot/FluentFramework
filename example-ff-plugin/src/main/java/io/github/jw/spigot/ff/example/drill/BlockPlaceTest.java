package io.github.jw.spigot.ff.example.drill;

import io.github.jwdeveloper.ff.core.spigot.player.PlayerUtils;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemApi;
import io.github.jwdeveloper.ff.extension.bai.items.impl.events.FluentItemUseEvent;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class BlockPlaceTest {

    private Map<Block, Entity> blockEntityMap = new HashMap<>();

    public void register(FluentApiSpigot fluentAPI) {
        var itemApi = fluentAPI.container().findInjection(FluentItemApi.class);
        var item = itemApi.addItem()
                .withEvents(events ->
                {
                    events.onRightClick(this::useEvent);
                }).withSchema(schema ->
                {
                    schema.withName("lead-ore");
                    schema.withCustomModelId(1);
                    schema.withMaterial(Material.PRISMARINE_SHARD);
                    schema.withDisplayName("Lead Ore");
                    schema.withStackable(false);
                }).buildAndRegister();

        fluentAPI.events().onEvent(BlockBreakEvent.class, blockBreakEvent ->
        {

            if(blockEntityMap.containsKey(blockBreakEvent.getBlock()))
            {
                var entity = blockEntityMap.get(blockBreakEvent.getBlock());
                entity.remove();
            }
        });

        fluentAPI.events().onEvent(BlockPlaceEvent.class, blockPlaceEvent ->
        {
            var itemInHand = blockPlaceEvent.getItemInHand();
            if (!item.isItemStack(itemInHand)) {
                return;
            }
            blockPlaceEvent.getBlock().setType(Material.BARRIER);
            ChopWood.createBlockDisplay(blockPlaceEvent.getBlock().getLocation(), Material.DIAMOND_BLOCK);
        });
    }

    public void useEvent(FluentItemUseEvent event) {
        if (!(event.getSpigotEvent() instanceof PlayerInteractEvent spigotEvent)) {
            return;
        }

        var block = spigotEvent.getClickedBlock().getLocation().add(0, 1, 0).getBlock();
        if (spigotEvent.getClickedBlock().getType().isAir()) {
            block = spigotEvent.getClickedBlock();
        }

        var entity = ChopWood.createBlockDisplay(block.getLocation(), Material.DIAMOND_BLOCK);
        block.setType(Material.BARRIER);
        blockEntityMap.put(block, entity);
    }


}
