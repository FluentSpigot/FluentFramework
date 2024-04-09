package io.github.jwdeveloper.ff.extension.bai.common.listeners;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockApi;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.events.FluentBlockClickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class BlockListener extends EventBase {
    private final FluentBlockApi blockApi;

    public BlockListener(Plugin plugin, FluentBlockApi blockApi) {
        super(plugin);
        this.blockApi = blockApi;
    }

    @EventHandler
    private void onBlockClick(PlayerInteractEvent event) {
        var actionResult = blockApi.fromMinecraftBlock(event.getClickedBlock());
        if (actionResult.isFailed()) {
            return;
        }
        var action = ItemUseListener.getUseAction(event);
        var blockInstance = actionResult.getContent();
        var fluentItemEvent = new FluentBlockClickEvent(blockInstance,
                event.getPlayer(),
                event.getBlockFace(),
                event.getAction(),
                event);
        switch (action) {
            case RIGHT -> blockInstance.getFluentBlock().events().getOnRightClick().invoke(fluentItemEvent);
            case LEFT -> blockInstance.getFluentBlock().events().getOnLeftClick().invoke(fluentItemEvent);
        }
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {
        var actionResult = blockApi.fromMinecraftBlock(event.getBlock());
        if (actionResult.isFailed()) {
            return;
        }
        var fluentBlockInstance = actionResult.getContent();
        fluentBlockInstance.destroy(event.getPlayer().getInventory().getItemInMainHand(), event.getPlayer());
    }

    /**
     * FluentItems has custom mechanism of placing blocks so, in order
     * to place default block event is canceled
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        var actionResult = blockApi.fromItemStack(event.getItemInHand());
        if (actionResult.isFailed()) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    private void onDamage(BlockDamageEvent event) {
        var actionResult = blockApi.fromMinecraftBlock(event.getBlock());
        if (actionResult.isFailed()) {
            return;
        }
        var blockInstance = actionResult.getContent();
        blockInstance.damage();
    }


    @EventHandler
    private void onExplode(BlockExplodeEvent event) {

    }
}
