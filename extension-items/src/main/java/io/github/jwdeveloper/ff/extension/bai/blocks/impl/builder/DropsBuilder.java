package io.github.jwdeveloper.ff.extension.bai.blocks.impl.builder;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockDropsBuilder;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockDrop;
import org.bukkit.inventory.ItemStack;

public class DropsBuilder implements BlockDropsBuilder {
    private final FluentBlockDrop drop;

    public DropsBuilder() {
        drop = new FluentBlockDrop();
    }

    public FluentBlockDrop build() {
        return drop;
    }

    @Override
    public void defaultDrop(ItemStack... itemStacks) {

    }
}
