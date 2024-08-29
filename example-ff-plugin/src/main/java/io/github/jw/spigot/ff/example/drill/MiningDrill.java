package io.github.jw.spigot.ff.example.drill;


import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemApi;
import io.github.jwdeveloper.ff.extension.bai.items.impl.events.FluentItemUseEvent;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.Damageable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiningDrill {

//    public void register(FluentApiSpigot fluentAPI) {
//        var itemApi = fluentAPI.container().findInjection(FluentItemApi.class);
//        var inv = fluentAPI.container().findInjection(InventoryApi.class);
//        FluentApi.events().onEvent(InventoryOpenEvent.class, event ->
//        {
//            if (!(event.getInventory().getHolder() instanceof StorageMinecart cart)) {
//                return;
//            }
//            event.setCancelled(true);
//            var item = event.getPlayer().getInventory().getItemInMainHand();
//            var fluentItem = itemApi.fromItemStack(item);
//            if (fluentItem.isFailed()) {
//                var comp = fluentAPI.container().findInjection(MiningCartInventory.class);
//                var inventory = inv.inventory().create(comp);
//                inventory.open((Player) event.getPlayer());
//                return;
//            }
//            var a = fluentItem.getObject();
//            if (a.getFluentItem().getSchema().getName().equals("mining-drill")) {
//                MiningCart.createMiningCart(cart);
//                return;
//            }
//
//
//        });
//
//        itemApi.addItem()
//                .withCrafting(crafting ->
//                {
//
//                })
//                .withEvents(events ->
//                {
//                    events.onRightClick(event ->
//                    {
//                        FluentLogger.LOGGER.info("Siema@");
//                        if (!(event.getSpigotEvent() instanceof PlayerInteractEvent spigotEvent)) {
//                            return;
//                        }
//
//                        handleBlockBreak(event);
//                    });
//                }).withSchema(schema ->
//                {
//                    schema.withName("mining-drill");
//                    schema.withCustomModelId(514);
//                    schema.withMaterial(Material.WOODEN_PICKAXE);
//                    schema.withDisplayName("Mining Drill");
//                    schema.withStackable(false);
//                }).buildAndRegister();
//
//    }

//    private void handleBlockBreak(FluentItemUseEvent event) {
//        if (!(event.getSpigotEvent() instanceof PlayerInteractEvent spigotEvent)) {
//            return;
//        }
//        var block = spigotEvent.getClickedBlock();
//        if (block == null) {
//            return;
//        }
//
//        var itemStack = event.getFluentItemStack().getItemStack();
//        event.getPlayer().playSound(event.getPlayer().getLocation(), "minecraft:drill",
//                SoundCategory.MASTER, 0.6f, 1.0F);
//        FluentApi.tasks().taskTimer(2, (iteration, task) ->
//                {
//                    var meta = itemStack.getItemMeta();
//                    var start = 514;
//                    var i = start + iteration % 3;
//                    if (meta instanceof Damageable damageable) {
//                        damageable.setDamage(damageable.getDamage() + 1);
//                    }
//                    meta.setCustomModelData(i);
//                    itemStack.setItemMeta(meta);
//                    FluentLogger.LOGGER.info("updating model!", i);
//                }).stopAfterIterations(10)
//                .start();
//
//        doDrill(block, spigotEvent.getBlockFace(), event.getPlayer());
//    }

//    public static void doDrill(Block block, BlockFace blockFace, Entity entity) {
//        getBlocks(block, blockFace)
//                .forEach(block1 ->
//                {
//                    sendBlockBreakingAnimation(block1, entity);
//                });
//    }


//    private static void sendBrakingPacket(int step, Block block) {
//        var manager = FluentApi.container().findInjection(ProtocolManager.class);
//        var blockBreakAnimation = manager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
//        var random = new Random();
//        blockBreakAnimation.getIntegers()
//                .write(0, random.nextInt())
//                .write(1, step );
//
//        BlockPosition position = new BlockPosition(block.getX(), block.getY(), block.getZ());
//        blockBreakAnimation.getBlockPositionModifier().write(0, position);
//        for (var onpl : Bukkit.getOnlinePlayers()) {
//            try {
//                manager.sendServerPacket(onpl, blockBreakAnimation);
//            } catch (InvocationTargetException e) {
//                FluentLogger.LOGGER.error(e);
//            }
//        }
//    }

//    private static void sendBlockBreakingAnimation(Block block, Entity player) {
//        block.getWorld().playSound(block.getLocation(), Sound.BLOCK_STONE_HIT, 0.5f, 1);
//
//        FluentApi.tasks().taskTimer(2, (iteration, task) ->
//                {
//                    sendBrakingPacket(iteration%9, block);
//                }).stopAfterIterations(10)
//                .onStop(simpleTaskTimer ->
//                {
//                    sendBrakingPacket(123, block);
//                    block.getWorld().playSound(block.getLocation(), Sound.BLOCK_STONE_HIT, 0.5f, 1);
//                    var drop = block.getDrops();
//                    for (var d : drop) {
//                        block.getWorld().dropItem(block.getLocation(), d);
//                    }
//
//                    var name = block.getType().name();
//                    var soundName = "BLOCK_" + name + "_BREAK";
//                    if (!block.getType().isAir()) {
//                        try {
//                            block.getWorld().playSound(block.getLocation(), Sound.valueOf(soundName), 0.5f, 1);
//                        } catch (Exception e) {
//                            block.getWorld().playSound(block.getLocation(), Sound.BLOCK_STONE_BREAK, 0.5f, 1);
//                        }
//                    }
//
//                    block.setType(Material.AIR);
//                    sendBrakingPacket(123, block);
//                })
//                .start();
//
//    }


    private static List<Block> getBlocks(Block block, BlockFace face) {
        List<Block> blocks = new ArrayList<>();
        if (block == null) {
            return blocks;
        }
        int modX = face.getModX();
        int modY = face.getModY();
        int modZ = face.getModZ();

        for (var a = -1; a <= 1; a++) {
            for (var b = -1; b <= 1; b++) {
                int dx = 0;
                int dy = 0;
                int dz = 0;

                if (modX != 0) {
                    dx = 0; // X is the face direction, iterate over Y-Z plane
                    dy = a;
                    dz = b;
                }
                if (modY != 0) {
                    dy = 0; // Y is the face direction, iterate over X-Z plane
                    dx = a;
                    dz = b;
                }
                if (modZ != 0) {
                    dz = 0; // Z is the face direction, iterate over X-Y plane
                    dx = a;
                    dy = b;
                }

                Block relativeBlock = block.getRelative(dx, dy, dz);
                if (!relativeBlock.getType().isSolid()) {
                    continue;
                }
                if (relativeBlock.getType().equals(Material.RAIL)) {
                    continue;
                }
                blocks.add(relativeBlock);
            }
        }
        return blocks;
    }
}
