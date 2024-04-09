package io.github.jwdeveloper.ff.extension.bai.blocks.impl;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlock;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockInstance;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.*;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.drop.FluentBlockDrop;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.drop.FluentBlockDrops;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.state.FluentBlockStates;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.events.FluentBlockPlacedEvent;
import io.github.jwdeveloper.ff.extension.bai.common.FrameworkSettings;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * BlockBreak animation
 * <p>
 * Ok I tried various method but non of them work but so far this is how I make it.
 * Not that good but somewhat acceptable for me:
 * <p>
 * - When user start mining catch it with PacketType.Play.Client.BLOCK_DIG packet
 * - If packet is Start Mining then send an Packet entity falling sand no gravity to the position and an barrier block to that same position
 * - Add some particle by send packet or normal to make it realistic
 * - Applies player with mining fatigue -1 to stop some weird bug or exploit
 * - Send packet block breaking stage to other player around them
 * - When certain of time pass without Abort Mining or Cancel Mining then invoke an custom BlockBreakEvent and BlockDropEvent
 * - Invoke block break naturally with player tool
 * - Send packet entity falling sand to y= -65 to kill it.
 */

public class SimpleBlock implements FluentBlock {
    private final FluentBlockEvents fluentBlockEvents;
    private final FluentBlockSchema fluentBlockSchema;
    private final FluentBlockSounds fluentBlockSounds;
    private final FluentBlockDrops fluentBlockDrop;
    private final FluentBlockStates fluentBlockState;
    private final FluentItem fluentItem;
    private final DisplayFactory displayFactory;

    public SimpleBlock(
            FluentItem fluentItem,
            FluentBlockStates fluentBlockStates,
            FluentBlockEvents fluentBlockEvents,
            FluentBlockSchema fluentBlockSchema,
            FluentBlockSounds fluentBlockSounds,
            FluentBlockDrops fluentBlockDrop,
            DisplayFactory displayFactory) {
        this.fluentBlockEvents = fluentBlockEvents;
        this.fluentBlockSchema = fluentBlockSchema;
        this.fluentBlockSounds = fluentBlockSounds;
        this.fluentBlockDrop = fluentBlockDrop;
        this.fluentItem = fluentItem;
        this.displayFactory = displayFactory;
        this.fluentBlockState = fluentBlockStates;
    }

    @Override
    public FluentBlockInstance placeAt(Location location) {
        var settings = FluentApi.container().findInjection(FrameworkSettings.class);
        var block = location.getBlock();
        block.setType(settings.getBlockMaterial());
        block.setMetadata("custom-block", new FixedMetadataValue(FluentApi.plugin(), fluentItem.getSchema().getName()));
        block.getLocation().getWorld().playSound(block.getLocation(), this.sounds().getPlace(), 1, 1);
        var blockDisplay = displayFactory.createDisplay(location, fluentItem.toItemStack());
        var instance = new SimpleBlockInstance(block, blockDisplay, this);
        events().getOnPlaced().invoke(new FluentBlockPlacedEvent(instance));
        return instance;
    }

    @Override
    public FluentBlockInstance placeAt(Player entity, Location location) {

        var item = entity.getInventory().getItemInMainHand();
        item.setAmount(item.getAmount() - 1);
        return placeAt(location);
    }

    @Override
    public FluentBlockDrops drop() {
        return fluentBlockDrop;
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

    @Override
    public FluentBlockStates states() {
        return fluentBlockState;
    }
}
