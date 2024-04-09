package io.github.jwdeveloper.ff.extension.bai.blocks.api;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.state.FluentBlockState;
import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public interface FluentBlockInstance {

    FluentBlock getFluentBlock();

    Display getDisplay();

    Block getBlock();

    void destroy();

    void damage();

    void destroy(ItemStack tool, Entity entity);

    void setDefaultState();

    void setState(String name);

    void setState(int index);

    FluentBlockState getState();
}
