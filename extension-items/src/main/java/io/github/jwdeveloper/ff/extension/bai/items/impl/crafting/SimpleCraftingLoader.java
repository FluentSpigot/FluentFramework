package io.github.jwdeveloper.ff.extension.bai.items.impl.crafting;

import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.items.api.crafting.FluentCrafting;
import io.github.jwdeveloper.ff.extension.bai.items.api.crafting.FluentCraftingBuilder;
import io.github.jwdeveloper.ff.extension.bai.items.api.crafting.FluentCraftingLoader;
import io.github.jwdeveloper.ff.extension.bai.items.api.crafting.FluentCraftingRegistry;
import io.github.jwdeveloper.ff.extension.bai.items.api.mappers.FluentCraftingMapper;
import org.bukkit.configuration.ConfigurationSection;

import java.util.function.Consumer;

public class SimpleCraftingLoader implements FluentCraftingLoader {

    private final FluentCraftingRegistry fluentCraftingRegistry;

    private final FluentCraftingMapper fluentCraftingMapper;
    private Consumer<FluentCraftingBuilder> builderConsumer = (x) -> {
    };
    private FluentItem fluentItem;

    public SimpleCraftingLoader(FluentCraftingRegistry fluentCraftingRegistry, FluentCraftingMapper fluentCraftingMapper) {
        this.fluentCraftingRegistry = fluentCraftingRegistry;
        this.fluentCraftingMapper = fluentCraftingMapper;
    }

    @Override
    public FluentCraftingLoader fromFluentItem(FluentItem section) {
        this.fluentItem = fluentItem;
        return this;
    }

    @Override
    public FluentCraftingLoader fromSection(ConfigurationSection section) {
        return this;
    }

    @Override
    public FluentCraftingLoader fromBuilder(Consumer<FluentCraftingBuilder> builderConsumer) {
        return this;
    }

    @Override
    public FluentCrafting register() {
        return null;
    }


}
