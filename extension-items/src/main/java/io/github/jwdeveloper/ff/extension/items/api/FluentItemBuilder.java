package io.github.jwdeveloper.ff.extension.items.api;

import io.github.jwdeveloper.ff.extension.items.api.crafting.FluentCraftingBuilder;
import io.github.jwdeveloper.ff.extension.items.api.schema.FluentItemSchemaBuilder;

import java.util.function.Consumer;

public interface FluentItemBuilder
{
    /**
     * Trigerred when shcema is loaded from yaml
     * @param builderConsumer
     * @return
     */
    FluentItemBuilder withSchema(Consumer<FluentItemSchemaBuilder> builderConsumer);

    /**
     * Trigerred when shcema is loaded from yaml
     * @param builderConsumer
     * @return
     */
    FluentItemBuilder withEvents(Consumer<FluentItemEvents> builderConsumer);

}
