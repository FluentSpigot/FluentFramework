package io.github.jwdeveloper.ff.extension.bai.items.impl;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockBuilder;
import io.github.jwdeveloper.ff.extension.bai.common.api.BehaviourBuilder;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCraftingApi;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.builder.CraftingSlotBuilder;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemBuilder;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemEvents;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemRegistry;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.builder.FluentCraftingBuilder;
import io.github.jwdeveloper.ff.extension.bai.items.api.mappers.FluentItemStackMapper;
import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemSchemaBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SimpleItemBuilder implements FluentItemBuilder {

    private final FluentItemSchemaBuilder schemaBuilder;
    private final FluentItemStackMapper itemStackMapper;
    private final FluentItemEvents events;
    private final FluentItemRegistry fluentItemRegistry;
    private final BlockBuilder blockBuilder;
    private final FluentCraftingBuilder craftingBuilder;
    private final Set<BehaviourBuilder> behaviourBuilders;
    private boolean isCraftingUsed;

    public SimpleItemBuilder(FluentItemSchemaBuilder schemaBuilder,
                             FluentItemStackMapper itemStackMapper,
                             FluentItemRegistry fluentItemRegistry) {
        this.fluentItemRegistry = fluentItemRegistry;
        this.schemaBuilder = schemaBuilder;
        this.itemStackMapper = itemStackMapper;
        this.events = new FluentItemEvents();
        this.behaviourBuilders = new HashSet<>();
        this.blockBuilder = FluentApi.container().findInjection(BlockBuilder.class);
        this.craftingBuilder = FluentApi.container().findInjection(FluentCraftingApi.class).addCrafting();

    }

    @Override
    public FluentItemBuilder withSchema(Consumer<FluentItemSchemaBuilder> builderConsumer) {
        builderConsumer.accept(schemaBuilder);
        return this;
    }


    @Override
    public FluentItemBuilder withEvents(Consumer<FluentItemEvents> builderConsumer) {
        builderConsumer.accept(events);
        return this;
    }

    @Override
    public FluentItemBuilder withCrafting(Consumer<CraftingSlotBuilder<FluentCraftingBuilder>> builderConsumer) {
        builderConsumer.accept(craftingBuilder);
        isCraftingUsed = true;
        return this;
    }


    public FluentItemBuilder makeBlock(Consumer<BlockBuilder> builderConsumer) {
        builderConsumer.accept(blockBuilder);
        behaviourBuilders.add(blockBuilder);
        return this;
    }

    @Override
    public FluentItemBuilder makeBlock() {
        behaviourBuilders.add(blockBuilder);
        return this;
    }

    @Override
    public FluentItemBuilder makeWeapon(Consumer<BlockBuilder> builderConsumer) {
        return null;
    }

    @Override
    public FluentItemBuilder makeWeapon() {
        return null;
    }

    @Override
    public FluentItemBuilder makeFood(Consumer<BlockBuilder> builderConsumer) {
        return null;
    }

    @Override
    public FluentItemBuilder makeFood() {
        return null;
    }


    public FluentItem build() {

        var schema = schemaBuilder.build();
        var item = new SimpleItem(schema, itemStackMapper, events);
        var behaviours = behaviourBuilders.stream().map(e -> e.build(item)).collect(Collectors.toSet());
        item.addBehaviour(behaviours);
        if (isCraftingUsed)
        {
            var crafting = craftingBuilder.withOutput(item).buildAndRegister();
            crafting.setFluentItem(item);
            crafting.setIdentifier(item.getSchema().getName());
        }
        return item;
    }

    public FluentItem buildAndRegister() {
        var item = build();
        fluentItemRegistry.register(item);
        return item;
    }
}
