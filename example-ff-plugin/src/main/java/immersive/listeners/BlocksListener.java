package immersive.listeners;

import immersive.BlocksHandler;
import immersive.common.ImmersiveBlockImpl;
import io.github.jwdeveloper.ff.core.cache.api.PluginCache;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.plugin.Plugin;


public class BlocksListener extends EventBase {
    private final BlocksHandler handler;
    private final PluginCache pluginCache;

    public BlocksListener(Plugin plugin, BlocksHandler handler, PluginCache pluginCache) {
        super(plugin);
        this.handler = handler;
        this.pluginCache = pluginCache;
    }



    @EventHandler
    public void onBlockDestroy(BlockBreakEvent event) {

        FluentLogger.LOGGER.info("DESTROYED");
        handler.handleDestroy(event.getBlock());
    }



    @EventHandler
    public void onUpdate(InventoryClickEvent event) {

        var inventory = event.getInventory();
        var location = inventory.getLocation();
        if (location == null) {
            return;
        }
        handler.handleUpdate(location.getBlock(), event.getInventory(),(Player) event.getWhoClicked());
    }

    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent event) {
        var inventory = event.getInventory();
        var location = inventory.getLocation();
        if (location == null) {
            return;
        }
        var block = location.getBlock();
        handler.handleOpen(block, inventory,(Player) event.getPlayer());
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        var inventory = event.getInventory();
        var location = inventory.getLocation();
        if (location == null) {
            return;
        }
        var block = location.getBlock();
        handler.handleClose(block, inventory,(Player) event.getPlayer());


       // event.getInventory().clear();
    }


    @EventHandler
    public void onInventoryCloseEvent2(InventoryCloseEvent event) {
      //  event.getInventory().clear();
    }

}
