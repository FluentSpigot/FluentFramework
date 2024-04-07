package io.github.jw.spigot.ff.example.drill;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.player.PlayerUtils;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemApi;
import io.github.jwdeveloper.ff.extension.bai.items.impl.events.FluentItemUseEvent;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.*;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class ChopWood {

    public void register(FluentApiSpigot fluentAPI) {
        var itemApi = fluentAPI.container().findInjection(FluentItemApi.class);
        var item = itemApi.addItem()
                .withEvents(events ->
                {
                    events.onLeftClick(this::handleHoopWood);
                }).withSchema(schema ->
                {
                    schema.withName("electric-saw");
                    schema.withCustomModelId(1);
                    schema.withMaterial(Material.DIAMOND_AXE);
                    schema.withDisplayName("Electric Saw");
                    schema.withStackable(false);
                }).buildAndRegister();

        fluentAPI.createCommand("saw")
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


    public void handleHoopWood(FluentItemUseEvent event) {
        if (!(event.getSpigotEvent() instanceof PlayerInteractEvent spigotEvent)) {
            return;
        }
        var block = spigotEvent.getClickedBlock();
        if (block == null) {
            return;
        }
        var material = block.getType();
        if (!Tag.LOGS.isTagged(material)) {
            return;
        }

        FluentLogger.LOGGER.info("Wood!");
        var iterator = new TreeBlockFinder();
        var treeBlocks = iterator.findConnectedTreeBlocks(block);
        treeBlocks.forEach(e ->
        {
            e.setType(Material.AIR);
            var display = createBlockDisplay(e.getLocation(), e.getType());

         /*   FluentApi.tasks().taskLater(() ->
            {
                //Scaling
                Transformation transform = display.getTransformation();
                transform.getScale().set(0.3f, 0.3f, 0.3f);
                display.setTransformation(transform);
            }, 20);*/

            FluentApi.tasks().taskTimer(60, (a,b) ->
            {
                //moving
                Transformation transform = display.getTransformation();
           /*     display.setInterpolationDuration(30);*/
                transform.getTranslation().add(0.2f, 0, 0);
                display.setTransformation(transform);

            }).start();
        });
    }

    public static BlockDisplay createBlockDisplay(Location spawnLoc, Material material) {
        BlockDisplay blockDisplay = (BlockDisplay) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.BLOCK_DISPLAY);
        blockDisplay.setBlock(material.createBlockData());
        Vector3f translation = new Vector3f(0, 0, 0);
        AxisAngle4f axisAngleRotMat = new AxisAngle4f((float) Math.PI, new Vector3f(0, 1, 0));
        Transformation transformation = new Transformation(
                translation,
                axisAngleRotMat,
                new Vector3f(1f, 1f, 1f),
                axisAngleRotMat
        );
        blockDisplay.setInterpolationDuration(25);
        blockDisplay.setTransformation(transformation);
        blockDisplay.setGlowing(true);

        FluentApi.events().onEvent(PluginDisableEvent.class, pluginDisableEvent ->
        {
            blockDisplay.remove();
        });
        return blockDisplay;
    }

    public Vector rotateAroundXAxis(Vector point, Vector origin, double angleDegrees) {
        // Translate point to origin
        Vector translatedPoint = point.clone().subtract(origin);

        // Convert angle to radians for trigonometric functions
        double angleRadians = Math.toRadians(angleDegrees);

        // Calculate cos and sin of angle for rotation
        double cos = Math.cos(angleRadians);
        double sin = Math.sin(angleRadians);

        // Perform rotation
        double y = translatedPoint.getY() * cos - translatedPoint.getZ() * sin;
        double z = translatedPoint.getY() * sin + translatedPoint.getZ() * cos;

        return new Vector(point.getX(), y, z);
    }
}
