package io.github.jwdeveloper.ff.extension.items.impl;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.items.api.FluentItemBuilder;
import io.github.jwdeveloper.ff.extension.items.api.FluentItemLoader;
import io.github.jwdeveloper.ff.extension.items.api.FluentItemRegistry;
import io.github.jwdeveloper.ff.extension.items.api.crafting.FluentCraftingBuilder;
import io.github.jwdeveloper.ff.extension.items.api.crafting.FluentCraftingLoader;
import io.github.jwdeveloper.ff.extension.items.api.crafting.FluentCraftingRegistry;
import io.github.jwdeveloper.ff.extension.items.api.mappers.FluentCraftingMapper;
import io.github.jwdeveloper.ff.extension.items.api.mappers.FluentItemStackMapper;
import io.github.jwdeveloper.ff.extension.items.api.mappers.FluentSchemaMapper;
import io.github.jwdeveloper.ff.extension.items.impl.crafting.SimpleCraftingBuilder;
import io.github.jwdeveloper.ff.extension.items.impl.mappers.CraftingMapper;
import io.github.jwdeveloper.ff.extension.items.impl.mappers.ScheamMapper;
import io.github.jwdeveloper.ff.extension.items.impl.schema.SchemaFactory;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SimpleItemLoader implements FluentItemLoader {

    private final FluentItemRegistry fluentItemRegistry;
    private final ConfigurationSection configurationSection;
    private final FluentItemStackMapper itemStackMapper;
    private final FluentCraftingMapper fluentCraftingMapper;

    private final FluentCraftingRegistry craftingRegistry;
    private FluentSchemaMapper schemaMapper;
    private Consumer<FluentItemBuilder> onItemBuildAction;
    private Consumer<FluentCraftingBuilder> onCraftingBuilder;

    public SimpleItemLoader(FluentItemRegistry fluentItemRegistry,
                            ConfigurationSection configurationSection,
                            FluentItemStackMapper itemStackMapper,
                            FluentCraftingMapper fluentCraftingMapper,
                            FluentCraftingRegistry craftingRegistry) {
        this.fluentItemRegistry = fluentItemRegistry;
        this.configurationSection = configurationSection;
        this.itemStackMapper = itemStackMapper;
        this.fluentCraftingMapper = fluentCraftingMapper;
        this.craftingRegistry = craftingRegistry;
        schemaMapper = new ScheamMapper();
        onItemBuildAction = (x) -> {
        };
        onCraftingBuilder = (x) -> {
        };
    }

    @Override
    public FluentItemLoader onItemSchemaMapping(FluentSchemaMapper mapping) {
        schemaMapper = mapping;
        return this;
    }

    @Override
    public FluentItemLoader onItemBuilder(Consumer<FluentItemBuilder> onBuild) {
        this.onItemBuildAction = onBuild;
        return this;
    }

    @Override
    public FluentItemLoader onCraftingBuilder(Consumer<FluentCraftingBuilder> onCraftingBuilder) {
        this.onCraftingBuilder = onCraftingBuilder;
        return this;
    }

    @Override
    public List<FluentItem> register() {
        //Method divided to 3 steps pipeline
        //  1. create FluentItems
        //  2. register FluentItems
        //  3. register Craftings
        var schemaFactory = new SchemaFactory(Map.of("DEFAULT", schemaMapper));
        var fluentItems = schemaFactory.loadFromConfig(configurationSection)
                .stream()
                .map(schema ->
                {
                    var builder = new SimpleItemBuilder(schema, itemStackMapper);
                    onItemBuildAction.accept(builder);
                    return builder;
                })
                .map(SimpleItemBuilder::build)
                .toList();

        fluentItems.forEach(fluentItemRegistry::register);
        for (var item : fluentItems)
        {
            var craftingData = item.getSchema().getCrafting();
            if (craftingData.isEmpty()) {
                continue;
            }
            var result = fluentCraftingMapper.map(craftingData, item);
            if (result.isFailed()) {
                FluentLogger.LOGGER.error("Unable to load crafting", item.getSchema().getName(), result.getMessage());
                continue;
            }
            var fluentCrafting = result.getContent();
            onCraftingBuilder.accept(new SimpleCraftingBuilder(fluentCrafting));
            craftingRegistry.register(fluentCrafting);
        }
        return fluentItems;
    }
}
