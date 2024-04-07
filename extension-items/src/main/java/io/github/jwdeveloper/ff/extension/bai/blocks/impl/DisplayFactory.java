package io.github.jwdeveloper.ff.extension.bai.blocks.impl;

import io.github.jwdeveloper.ff.core.cache.api.PluginCache;
import io.github.jwdeveloper.ff.core.spigot.events.api.FluentEventManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class DisplayFactory {
    private final PluginCache pluginCache;
    private final FluentEventManager fluentEventManager;

    public DisplayFactory(PluginCache pluginCache, FluentEventManager fluentEventManager) {
        this.pluginCache = pluginCache;
        this.fluentEventManager = fluentEventManager;
    }

    public Display createDisplay(Location spawnLoc, ItemStack itemStack) {
        ItemDisplay blockDisplay = (ItemDisplay) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.ITEM_DISPLAY);
        blockDisplay.setItemStack(itemStack);
        Vector3f translation = new Vector3f(0.5f, 0.5f, 0.5f);
        AxisAngle4f axisAngleRotMat = new AxisAngle4f((float) Math.PI, new Vector3f(0, 1, 0));
        Transformation transformation = new Transformation(
                translation,
                axisAngleRotMat,
                new Vector3f(1f, 1f, 1f),
                axisAngleRotMat
        );
        blockDisplay.setInterpolationDuration(25);
        blockDisplay.setTransformation(transformation);
        //blockDisplay.setGlowing(true);
        blockDisplay.setPersistent(false);

        pluginCache.set(spawnLoc.toString(),blockDisplay);
        fluentEventManager.onEvent(PluginDisableEvent.class, pluginDisableEvent ->
        {
            blockDisplay.remove();
        });
        return blockDisplay;
    }
}
