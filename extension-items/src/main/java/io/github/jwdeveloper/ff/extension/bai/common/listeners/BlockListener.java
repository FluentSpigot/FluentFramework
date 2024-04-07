package io.github.jwdeveloper.ff.extension.bai.common.listeners;

import io.github.jwdeveloper.ff.core.cache.api.PluginCache;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockApi;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemApi;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

public class BlockListener extends EventBase {
    private final FluentBlockApi blockApi;

    public BlockListener(Plugin plugin, FluentBlockApi blockApi) {
        super(plugin);
        this.blockApi = blockApi;
    }

    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {
        var actionResult = blockApi.fromMinecraftBlock(event.getBlock());
        if (actionResult.isFailed()) {
            return;
        }
        var fluentBlockInstance = actionResult.getContent();
        fluentBlockInstance.destroy();
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

    }


    @EventHandler
    private void onExplode(BlockExplodeEvent event) {

    }
}
