package io.github.jwdeveloper.ff.extension.bai.blocks.impl;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlock;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockInstance;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockDrop;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockEvents;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockSchema;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockSounds;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class SimpleBlock implements FluentBlock {
    private final FluentBlockEvents fluentBlockEvents;
    private final FluentBlockSchema fluentBlockSchema;
    private final FluentBlockSounds fluentBlockSounds;
    private final FluentBlockDrop fluentBlockDrop;
    private final FluentItem fluentItem;
    private final DisplayFactory displayFactory;

    public SimpleBlock(
            FluentItem fluentItem,
            FluentBlockEvents fluentBlockEvents,
            FluentBlockSchema fluentBlockSchema,
            FluentBlockSounds fluentBlockSounds,
            FluentBlockDrop fluentBlockDrop,
            DisplayFactory displayFactory) {
        this.fluentBlockEvents = fluentBlockEvents;
        this.fluentBlockSchema = fluentBlockSchema;
        this.fluentBlockSounds = fluentBlockSounds;
        this.fluentBlockDrop = fluentBlockDrop;
        this.fluentItem = fluentItem;
        this.displayFactory = displayFactory;
    }

    @Override
    public FluentBlockInstance spawnAt(Location location) {
        var block = location.getBlock();
        block.setType(Material.BARRIER);
        block.setMetadata("custom-block", new FixedMetadataValue(FluentApi.plugin(), fluentItem.getSchema().getName()));
        var blockDisplay = displayFactory.createDisplay(location, fluentItem.toItemStack());
        return new SimpleBlockInstance(block, blockDisplay, this);
    }

    @Override
    public ItemStack[] drop(Entity entity, ItemStack itemStack) {
        return new ItemStack[0];
    }

    @Override
    public FluentItem fluentItem() {
        return fluentItem;
    }

    @Override
    public FluentBlockSchema schema() {
        return fluentBlockSchema;
    }

    @Override
    public FluentBlockSounds sounds() {
        return fluentBlockSounds;
    }

    @Override
    public FluentBlockEvents events() {
        return fluentBlockEvents;
    }
}
