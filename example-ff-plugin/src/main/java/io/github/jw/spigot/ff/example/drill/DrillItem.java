package io.github.jw.spigot.ff.example.drill;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.wrappers.BlockPosition;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.player.PlayerUtils;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemApi;
import io.github.jwdeveloper.ff.extension.bai.items.impl.events.FluentItemUseEvent;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrillItem
{

    public void register(FluentApiSpigot fluentAPI)
    {
        var itemApi = fluentAPI.container().findInjection(FluentItemApi.class);
        var item = itemApi.addItem()
                .withEvents(events ->
                {
                    events.onLeftClick(this::handleBlockBreak);
                }).withSchema(schema ->
                {
                    schema.withName("mining-drill");
                    schema.withCustomModelId(1);
                    schema.withMaterial(Material.DIAMOND_PICKAXE);
                    schema.withDisplayName("Mining Drill");
                    schema.withStackable(false);
                }).buildAndRegister();

        fluentAPI.createCommand("drill")
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onPlayerExecute(playerCommandEvent ->
                    {
                        var itemStack = item.toItemStack();
                        var player = playerCommandEvent.getPlayer();
                        PlayerUtils.giveItem(player, itemStack);
                    });
                }).buildAndRegister();
    }

    private void handleBlockBreak(FluentItemUseEvent event) {
        if (!(event.getSpigotEvent() instanceof PlayerInteractEvent spigotEvent)) {
            return;
        }
        var block = spigotEvent.getClickedBlock();
        if (block == null) {
            return;
        }

        getBlocks(block, spigotEvent.getBlockFace())
                .forEach(block1 ->
                {
                    sendBlockBreakingAnimation(block1, spigotEvent.getPlayer());
                });
    }


    private void sendBlockBreakingAnimation(Block block, Player player) {
        FluentApi.tasks().taskTimer(2, (iteration, task) ->
                {
                    var manager = FluentApi.container().findInjection(ProtocolManager.class);
                    var blockBreakAnimation = manager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
                    int entityId = new Random().nextInt(Integer.MAX_VALUE);

                    blockBreakAnimation.getIntegers()
                            .write(0, entityId)
                            .write(1, iteration);


                    BlockPosition position = new BlockPosition(block.getX(), block.getY(), block.getZ());
                    blockBreakAnimation.getBlockPositionModifier().write(0, position);
                    try {
                        manager.sendServerPacket(player, blockBreakAnimation);
                    } catch (InvocationTargetException e) {
                        FluentLogger.LOGGER.error(e);
                    }
                }).stopAfterIterations(10)
                .onStop(simpleTaskTimer ->
                {
                    block.setType(Material.AIR);
                })
                .start();
    }

    private List<Block> getBlocks(Block block, BlockFace face) {
        if (block == null) {
            return List.of();
        }

        FluentLogger.LOGGER.info(face.getModX());
        var world = block.getWorld();

        var blocks = new ArrayList<Block>();

        var xx = block.getX();
        var yy = block.getY();
        var zz = block.getZ();
        for (var x = xx - 1; x <= xx + 1; x++) {
            for (var y = yy - 1; y <= yy + 1; y++) {
                var currentBlock = world.getBlockAt(x, y, zz);
                blocks.add(currentBlock);
            }
        }
        return blocks;
    }
}
