package io.github.jwdeveloper.ff.extension.bai.items.api;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockBuilder;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.builder.CraftingSlotBuilder;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.builder.FluentCraftingBuilder;
import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemSchemaBuilder;

import java.util.function.Consumer;

public interface FluentItemBuilder {
    /**
     * Trigerred when shcema is loaded from yaml
     *
     * @param builderConsumer
     * @return
     */
    FluentItemBuilder withSchema(Consumer<FluentItemSchemaBuilder> builderConsumer);

    /**
     * Trigerred when shcema is loaded from yaml
     *
     * @param builderConsumer
     * @return
     */
    FluentItemBuilder withEvents(Consumer<FluentItemEvents> builderConsumer);

    FluentItemBuilder withCrafting(Consumer<CraftingSlotBuilder<FluentCraftingBuilder>> builderConsumer);

    FluentItemBuilder makeBlock(Consumer<BlockBuilder> builderConsumer);

    FluentItemBuilder makeBlock();

    FluentItemBuilder makeWeapon(Consumer<BlockBuilder> builderConsumer);

    FluentItemBuilder makeWeapon();

    FluentItemBuilder makeFood(Consumer<BlockBuilder> builderConsumer);

    FluentItemBuilder makeFood();

    FluentItem build();

    FluentItem buildAndRegister();


}
