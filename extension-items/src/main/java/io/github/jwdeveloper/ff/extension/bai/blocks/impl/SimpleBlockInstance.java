package io.github.jwdeveloper.ff.extension.bai.blocks.impl;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlock;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockInstance;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;



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

    public Block getBlock()
    {
        return block;
    }

    public Display getDisplay()
    {
        return display;
    }


    public FluentBlock getFluentBlock()
    {
        return fluentBlock;
    }

    @Override
    public void destroy()
    {
        block.setType(Material.AIR);
        display.remove();
    }

    @Override
    public void destroy(ItemStack tool, Entity entity) {
        destroy();
    }
}
