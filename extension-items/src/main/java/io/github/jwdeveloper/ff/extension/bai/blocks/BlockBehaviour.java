package io.github.jwdeveloper.ff.extension.bai.blocks;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlock;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockRegistry;
import io.github.jwdeveloper.ff.extension.bai.common.api.FluentItemBehaviour;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.items.impl.events.FluentItemUseEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockBehaviour implements FluentItemBehaviour {
    private final FluentBlock fluentBlock;
    private final FluentBlockRegistry registry;

    public BlockBehaviour(FluentBlock fluentBlock, FluentBlockRegistry registry) {
        this.fluentBlock = fluentBlock;
        this.registry = registry;
    }

    @Override
    public void register(FluentItem fluentItem) {
        fluentItem.events().onRightClick(this::handleBlockPlace);
        registry.register(fluentBlock);
    }

    @Override
    public void unregister(FluentItem fluentItem) {
        registry.register(fluentBlock);
    }

    private boolean justPlaced = false;
    public void handleBlockPlace(FluentItemUseEvent event) {
        if (!(event.getSpigotEvent() instanceof PlayerInteractEvent spigotEvent)) {
            return;
        }
        if(justPlaced == true)
        {
            justPlaced = false;
            return;
        }
        var clickedBlock = spigotEvent.getClickedBlock();
        if(clickedBlock == null || clickedBlock.getType().isAir())
        {
            return;
        }

        var location = spigotEvent.getClickedBlock().getLocation();
        fluentBlock.placeAt(event.getPlayer(), location.add(spigotEvent.getBlockFace().getDirection()));
        justPlaced = true;
        event.setCancelled(true);
    }
}
