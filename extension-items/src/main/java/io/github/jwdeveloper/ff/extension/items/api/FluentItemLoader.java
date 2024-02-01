package io.github.jwdeveloper.ff.extension.items.api;

import io.github.jwdeveloper.ff.extension.items.api.crafting.FluentCraftingBuilder;
import io.github.jwdeveloper.ff.extension.items.api.mappers.FluentCraftingMapper;
import io.github.jwdeveloper.ff.extension.items.api.mappers.FluentSchemaMapper;

import java.util.List;
import java.util.function.Consumer;

public interface FluentItemLoader {

    /**
     * @param mapping invoked when config section is being mapped to itemSchema
     * @return
     */
    FluentItemLoader onItemSchemaMapping(FluentSchemaMapper mapping);

    /**
     * @param onBuild invoked when item is being build
     * @return
     */
    FluentItemLoader onItemBuilder(Consumer<FluentItemBuilder> onBuild);


    /**
     * triggered when crafting yaml data is mapped to crafting object
     *
     * @param mapping crafting mapper implementation
     * @return
     */
    FluentItemLoader onCraftingBuilder(Consumer<FluentCraftingBuilder> onCraftingBuilder);

    /**
     * Register loaded items to the items registry, so they can be use
     *
     * @return list of loaded items
     */
    List<FluentItem> register();
}
