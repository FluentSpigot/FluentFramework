package io.github.jwdeveloper.ff.extension.bai.blocks.api;

import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public interface FluentBlockInstance {

    FluentBlock getFluentBlock();

    Display getDisplay();

    Block getBlock();

    void destroy();
    void destroy(ItemStack tool, Entity entity);
}
