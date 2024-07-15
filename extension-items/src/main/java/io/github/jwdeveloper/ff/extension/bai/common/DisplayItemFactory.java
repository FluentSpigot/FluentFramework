package io.github.jwdeveloper.ff.extension.bai.common;

import io.github.jwdeveloper.ff.core.cache.api.PluginCache;
import io.github.jwdeveloper.ff.core.spigot.events.api.FluentEventManager;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class DisplayItemFactory {
    private final PluginCache pluginCache;
    private final FluentEventManager fluentEventManager;

    public DisplayItemFactory(PluginCache pluginCache, FluentEventManager fluentEventManager) {
        this.pluginCache = pluginCache;
        this.fluentEventManager = fluentEventManager;
    }

    public Display createDisplay(Location spawnLoc, ItemStack itemStack) {
        ItemDisplay blockDisplay = (ItemDisplay) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.ITEM_DISPLAY);
        var translation = new Vector3f(0.5f, 0.5f, 0.5f);
        var axisAngleRotMat = new AxisAngle4f((float) Math.PI, new Vector3f(0, 1, 0));
        var transformation = new Transformation(
                translation,
                axisAngleRotMat,
                new Vector3f(1, 1, 1),
                axisAngleRotMat
        );
        blockDisplay.setItemStack(itemStack);
        blockDisplay.setInterpolationDuration(0);
        blockDisplay.setTransformation(transformation);
        // blockDisplay.setBrightness(new Display.Brightness(15, 15));
        //blockDisplay.setGlowing(true);
        blockDisplay.setPersistent(false);
        pluginCache.set(spawnLoc.toString(), blockDisplay);
        fluentEventManager.onEvent(PluginDisableEvent.class, pluginDisableEvent ->
        {
            blockDisplay.remove();
        });
        return blockDisplay;
    }
}
