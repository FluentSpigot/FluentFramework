package io.github.jwdeveloper.ff.extension.bai.blocks.impl.builder;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockDropsBuilder;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.drop.FluentBlockDrop;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.drop.FluentBlockDrops;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemRegistry;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public class DropsBuilder implements BlockDropsBuilder {
    private final FluentBlockDrops drop;
    private final FluentItemRegistry fluentItemRegistry;

    public DropsBuilder(FluentItemRegistry fluentItemRegistry) {
        drop = new FluentBlockDrops();
        this.fluentItemRegistry = fluentItemRegistry;
    }

    public FluentBlockDrops build() {
        return drop;
    }

    @Override
    public BlockDropsBuilder addDefaultDrop(ItemStack... itemStacks) {
        for (var item : itemStacks) {
            addDrop(item, 1);
        }
        return this;
    }

    public BlockDropsBuilder addDrop(FluentItem fluentItem, float probability) {
        return addDrop(fluentItem.toItemStack(), probability);
    }

    public BlockDropsBuilder addDrop(Function<FluentItemRegistry, FluentItem> function, float probability) {
        return addDrop(function.apply(fluentItemRegistry), probability);
    }

    public BlockDropsBuilder addDrop(ItemStack itemStack, float probability) {

        drop.addDrop(new FluentBlockDrop(itemStack, probability));
        return this;
    }


}
