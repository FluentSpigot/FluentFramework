package immersive.listeners;

import io.github.jwdeveloper.ff.core.common.TransformationUtility;
import io.github.jwdeveloper.ff.core.common.java.MathUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.displays.DisplayUtils;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import net.minecraft.world.entity.vehicle.MinecartChest;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class PlayerListener extends EventBase {

    public PlayerListener(Plugin plugin) {
        super(plugin);
        playerRayTrace();
    }


    public void playerRayTrace() {
        FluentApi.tasks().taskTimer(20, (iteration, task) ->
        {
            for (var player : Bukkit.getOnlinePlayers()) {
                var block = player.getTargetBlockExact(4);
                if (block != null) {
                    if(handleBlock(block, player))
                    {
                        return;
                    }
                }
                var entitis = player.getNearbyEntities(4, 4, 4);
                for (var entity : entitis) {

                    if(handleEntity(entity, player))
                    {
                        return;
                    }
                }
            }


        }).start();
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent event) {
        FluentLogger.LOGGER.info("0");
        if (event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
        var clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) {
            return;
        }

        var res = handleBlock(clickedBlock, event.getPlayer());
        if (res) {
            event.setCancelled(true);
        }
    }

    public boolean handleBlock(Block clickedBlock, Player player) {
        if (clickedBlock == null) {
            return false;
        }

        var state = clickedBlock.getState();
        if (!(state instanceof InventoryHolder container)) {
            FluentApi.cache().<Entity>removeIfPresent("root", object ->
            {
                for (var passager : object.getPassengers()) {
                    passager.remove();
                }
                object.remove();
            });
            FluentApi.cache().remove("root-task");
            return false;
        }
        return handleShowInventory(container, player);
    }

    private boolean handleEntity(Entity entity, Player player) {
        if (entity == null) {
            return false;
        }

        if(entity instanceof Display)
        {
            return false;
        }

        if (!(entity instanceof InventoryHolder container)) {
            FluentApi.cache().<Entity>removeIfPresent("root", object ->
            {
                for (var passager : object.getPassengers()) {
                    passager.remove();
                }
                object.remove();
            });
            FluentApi.cache().remove("root-task");
            return false;
        }
        return handleShowInventory(container, player);
    }

    public boolean handleShowInventory(InventoryHolder holder, Player player) {

        var inventory = holder.getInventory();
        var location = player.getLocation();
        var root = FluentApi.cache().getOrCreate("root", () ->
        {
            return DisplayUtils.newItemDisplay(location);
        });
        for (var passager : root.getPassengers()) {
            passager.remove();
        }
        for (var i = inventory.getSize() - 1; i >= 0; i--) {

            var itemstack = inventory.getItem(i);
            var item = DisplayUtils.newItemDisplay(location);
            var text = DisplayUtils.newTextDisplay(location, "");

            if (itemstack != null) {
                text.setText(itemstack.getAmount() + "");
            }

            item.setItemStack(inventory.getItem(i));


            var position = MathUtils.indexToVector2(i, 9);
            var scale = 0.2f;

            var xOffset = scale * -position.getX();
            var yOffset = scale * -position.getY();

            var tranform = item.getTransformation();
            tranform.getScale().set(0.1f, 0.1f, 0.1f);
            tranform.getTranslation().set(xOffset, yOffset, 2);
            tranform.getRightRotation().set(new AxisAngle4f((float) Math.toRadians(290), (float) 0.5, 0, (float) 0.5));
            item.setTransformation(tranform);


            var c = TransformationUtility.create(tranform);
            c.setRightRotation(180, 0, 1, 0);
            c.addTranslation(new Vector3f(-0.1f, -0.1f, 0));
            c.setScale(0.3f, 0.3f, 0.3f);
            text.setTransformation(c.build());
            text.setBackgroundColor(Color.GREEN);
            root.addPassenger(item);
            root.addPassenger(text);
            FluentApi.tasks().taskTimer(1, (iteration, task) ->
            {
                item.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
                text.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
            }).start();
            FluentApi.cache().set(text);
            FluentApi.cache().set(item);
        }
        var task = FluentApi.tasks().taskTimer(1, (iteration, t) ->
        {
            root.setRotation(player.getLocation().getYaw(), player.getLocation().getPitch());
        }).start();
        FluentApi.cache().set("root-task", task);
        player.addPassenger(root);
        return true;
    }
}
