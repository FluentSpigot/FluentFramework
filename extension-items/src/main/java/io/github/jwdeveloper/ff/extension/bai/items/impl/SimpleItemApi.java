package io.github.jwdeveloper.ff.extension.bai.items.impl;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.extension.bai.items.api.*;
import io.github.jwdeveloper.ff.extension.bai.common.FrameworkSettings;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCraftingRegistry;
import io.github.jwdeveloper.ff.extension.bai.items.api.itemstack.FluentItemStack;
import io.github.jwdeveloper.ff.extension.bai.items.api.mappers.FluentItemStackMapper;
import io.github.jwdeveloper.ff.extension.bai.items.impl.mappers.CraftingMapper;
import io.github.jwdeveloper.ff.extension.bai.items.impl.mappers.ItemStackMapper;
import io.github.jwdeveloper.ff.extension.bai.items.impl.schema.SchemaBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

public class SimpleItemApi implements FluentItemApi {
    private final FluentItemRegistry itemsRegistry;
    private final FluentItemStackMapper itemStackMapper;
    private final FrameworkSettings settings;
    private final FluentCraftingRegistry craftingsRegistry;

    public SimpleItemApi(FluentItemRegistry registry,
                         FluentItemStackMapper itemStackMapper,
                         FrameworkSettings settings,
                         FluentCraftingRegistry fluentCraftingRegistry) {
        this.itemsRegistry = registry;
        this.itemStackMapper = itemStackMapper;
        this.settings = settings;
        this.craftingsRegistry = fluentCraftingRegistry;
    }


    @Override
    public FrameworkSettings settings() {
        return settings;
    }

    @Override
    public void reset() {
        itemsRegistry.reset();
        craftingsRegistry.reset();
    }


    @Override
    public FluentItemLoader addItems(ConfigurationSection section) {
        return new SimpleItemLoader(itemsRegistry,
                section,
                itemStackMapper,
                new CraftingMapper(itemsRegistry, settings),
                craftingsRegistry);
    }

    @Override
    public FluentItemBuilder addItem() {

        return new SimpleItemBuilder(new SchemaBuilder(), new ItemStackMapper(itemsRegistry, settings), itemsRegistry);
    }

    @Override
    public List<FluentItem> findItems() {
        return itemsRegistry.findAll();
    }

    @Override
    public List<FluentItem> findItemsByTag(String tag) {
        return itemsRegistry.findByTag(tag);
    }


    @Override
    public ActionResult<FluentItemStack> fromItemStack(ItemStack itemStack) {
        return itemStackMapper.fromItemStack(itemStack);
    }

    @Override
    public Optional<FluentItem> findFluentItem(String uniqueName) {
        return itemsRegistry.findByName(uniqueName);
    }

}
