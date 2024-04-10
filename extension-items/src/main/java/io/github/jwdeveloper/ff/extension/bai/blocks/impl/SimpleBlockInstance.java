package io.github.jwdeveloper.ff.extension.bai.blocks.impl;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlock;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockInstance;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.state.FluentBlockState;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.events.FluentBlockDamageEvent;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.events.FluentBlockDestoryEvent;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.events.FluentBlockStateEvent;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Random;


@Setter
public class SimpleBlockInstance implements FluentBlockInstance {
    private Block block;
    private Display display;
    private FluentBlock fluentBlock;


    public SimpleBlockInstance(Block block, Display blockDisplay, FluentBlock fluentBlock) {
        this.block = block;
        this.display = blockDisplay;
        this.fluentBlock = fluentBlock;
    }

    public Block getBlock() {
        return block;
    }

    public Display getDisplay() {
        return display;
    }


    public FluentBlock getFluentBlock() {
        return fluentBlock;
    }

    @Override
    public void destroy() {
        fluentBlock.events().getOnDestroyed().invoke(new FluentBlockDestoryEvent(this));
        block.setType(Material.AIR);
        block.getLocation().getWorld().playSound(block.getLocation(), fluentBlock.sounds().getDestroy(), 1, 1);
        display.remove();
    }

    @Override
    public void damage() {
        fluentBlock.events().getOnDamage().invoke(new FluentBlockDamageEvent(this));
        block.getLocation().getWorld().playSound(block.getLocation(), fluentBlock.sounds().getDamage(), 1, 1);
    }

    @Override
    public void destroy(ItemStack tool, Entity entity) {
        var drops = fluentBlock.drop().calculateDrop(tool, entity);
        var loc = block.getLocation();
        for (var item : drops) {
            loc.getWorld().dropItem(loc, item);
        }
        destroy();
    }


    @Override
    public void setState(String name) {
        var optional = fluentBlock.states().findByName(name);
        if (optional.isEmpty()) {
            return;
        }
        updateState(optional.get());
    }

    @Override
    public void setState(int index) {
        var optional = fluentBlock.states().findByIndex(index);
        if (optional.isEmpty()) {
            return;
        }
        updateState(optional.get());
    }

    @Override
    public FluentBlockState getState() {
        var meta = block.getMetadata("state");
        if (meta.isEmpty()) {
            return createDefaultState();
        }
        var value = meta.get(0);
        var optional = fluentBlock.states().findByName(value.asString());
        if (optional.isEmpty()) {
            return createDefaultState();
        }
        return optional.get();
    }

    @Override
    public void setDefaultState() {

        var defaultState = fluentBlock.states().getDefaultState();
        if (defaultState == null) {
            updateState(createDefaultState());
            return;
        }
        updateState(defaultState);
    }

    private void updateState(FluentBlockState state) {

        var itemDisplay = (ItemDisplay) display;
        var itemStack = itemDisplay.getItemStack();

        if (!state.getMaterial().isAir()) {
            itemStack.setType(state.getMaterial());
        }
        if (state.getCustomModelId() != -1) {
            var meta = itemStack.getItemMeta();
            meta.setCustomModelData(state.getCustomModelId());
            itemStack.setItemMeta(meta);
        }
        itemDisplay.setItemStack(itemStack);
        block.setMetadata("state", new FixedMetadataValue(FluentApi.plugin(), state.getName()));
        fluentBlock.events().getOnStateUpdated().invoke(new FluentBlockStateEvent(this, state));
    }

    private FluentBlockState createDefaultState() {
        var data = new FluentBlockState();
        data.setIndex(0);
        data.setName("default");
        data.setMaterial(fluentBlock.fluentItem().getSchema().getMaterial());
        data.setCustomModelId(fluentBlock.fluentItem().getSchema().getCustomModelId());
        return data;
    }
}
