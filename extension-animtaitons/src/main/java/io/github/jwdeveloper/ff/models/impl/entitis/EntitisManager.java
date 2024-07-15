package io.github.jwdeveloper.ff.models.impl.entitis;

import io.github.jwdeveloper.ff.core.common.TransformationUtility;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.displays.DisplayUtils;
import io.github.jwdeveloper.ff.models.api.FluentDisplayModel;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EntitisManager {

    private final FluentDisplayModel fluentDisplayModel;
    private List<Entity> entitis = new ArrayList<>();

    public EntitisManager(Location location, FluentDisplayModel fluentDisplayModel) {
        this.fluentDisplayModel = fluentDisplayModel;
        entitis = new ArrayList<>();
        init(location);
    }

    public void teleport(Location location) {
        entitis.forEach(e -> e.teleport(location));
    }

    public void remove() {
        entitis.forEach(e -> e.remove());
    }

    public Entity[] getAll() {
        return entitis.toArray(new Entity[0]);
    }


    private void init(Location location) {
        for (var entry : fluentDisplayModel.getBones().entrySet()) {
            var bone = entry.getValue();
            //     var loc = location.clone().add(bone.getPivot());
            for (var cube : bone.getCubes()) {
                addEntity(bone.getName(), location, display -> {
                    var builder = TransformationUtility.create();
                    FluentLogger.LOGGER.info("Loaded cube!", cube, bone.getName());
                    builder.setScale(cube.getScale());
                    builder.setRightRotation(cube.getRotation());
                    builder.setTranslation(cube.getTransform());

                    display.setInterpolationDelay(-1);
                    display.setInterpolationDuration(20);
                    display.setTransformation(builder.build());
                });

            }
        }
    }

    public ItemDisplay addEntity(String boneName, Location location, Consumer<ItemDisplay> displayConsumer) {
        var display = DisplayUtils.newItemDisplay(new ItemStack(Material.DIAMOND_BLOCK), location);
        display.setMetadata("bone-name", new FixedMetadataValue(FluentApi.plugin(), boneName));
        entitis.add(display);
        FluentApi.tasks().taskLater(() ->
        {
            displayConsumer.accept(display);
        }, 1);
        return display;
    }
}
