package io.github.jwdeveloper.ff.extension.items.impl;

import io.github.jwdeveloper.ff.extension.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.items.api.FluentItemBuilder;
import io.github.jwdeveloper.ff.extension.items.api.FluentItemEvents;
import io.github.jwdeveloper.ff.extension.items.api.crafting.FluentCraftingBuilder;
import io.github.jwdeveloper.ff.extension.items.api.mappers.FluentItemStackMapper;
import io.github.jwdeveloper.ff.extension.items.api.schema.FluentItemSchemaBuilder;
import io.github.jwdeveloper.ff.extension.items.impl.crafting.SimpleCraftingBuilder;

import java.util.function.Consumer;

public class SimpleItemBuilder implements FluentItemBuilder {

    private final FluentItemSchemaBuilder schemaBuilder;
    private final FluentItemStackMapper itemStackMapper;
    private final FluentItemEvents events;

    public SimpleItemBuilder(FluentItemSchemaBuilder schemaBuilder, FluentItemStackMapper itemStackMapper) {
        this.schemaBuilder = schemaBuilder;
        this.itemStackMapper = itemStackMapper;
        this.events = new FluentItemEvents();
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

    public FluentItem build() {
        var schema = schemaBuilder.build();
        return new SimpleItem(schema, itemStackMapper, events);
    }
}
