package io.github.jwdeveloper.ff.extension.bai.blocks.api.builder;

import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemRegistry;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public interface BlockDropsBuilder {

    BlockDropsBuilder addDefaultDrop(ItemStack... itemStacks);

    /**
     * @param fluentItem
     * @param probability range 0.0 <-> 1.0
     */
    BlockDropsBuilder addDrop(FluentItem fluentItem, float probability);

    BlockDropsBuilder addDrop(Function<FluentItemRegistry, FluentItem> function, float probability);

    BlockDropsBuilder addDrop(ItemStack itemStack, float probability);
}
