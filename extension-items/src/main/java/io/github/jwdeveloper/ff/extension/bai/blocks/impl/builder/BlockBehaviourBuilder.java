package io.github.jwdeveloper.ff.extension.bai.blocks.impl.builder;

import io.github.jwdeveloper.ff.extension.bai.blocks.BlockBehaviour;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockRegistry;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.*;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockSchema;
import io.github.jwdeveloper.ff.extension.bai.common.DisplayItemFactory;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.SimpleBlock;
import io.github.jwdeveloper.ff.extension.bai.common.api.FluentItemBehaviour;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemRegistry;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;

public class BlockBehaviourBuilder implements BlockBuilder {

    private final DropsBuilder dropsBuilder;
    private final EventsBuilder eventsBuilder;
    private final SoundsBuilder soundsBuilder;
    private final StatesBuilder statesBuilder;
    private final FluentBlockSchema fluentBlockSchema;
    private final DisplayItemFactory displayFactory;
    private final FluentBlockRegistry registry;


    public BlockBehaviourBuilder(FluentBlockRegistry simpleBlockRegistry, DisplayItemFactory displayFactory)
    {
        dropsBuilder = new DropsBuilder(FluentApi.container().findInjection(FluentItemRegistry.class));
        eventsBuilder = new EventsBuilder();
        soundsBuilder = new SoundsBuilder();
        statesBuilder = new StatesBuilder();
        this.fluentBlockSchema = new FluentBlockSchema();
        this.displayFactory = displayFactory;
        this.registry = simpleBlockRegistry;
    }

    @Override
    public BlockBuilder withHardness(int hardness) {
        fluentBlockSchema.setHardness(hardness);
        return this;
    }

    @Override
    public BlockStatesBuilder withStates() {
        return statesBuilder;
    }

    @Override
    public BlockDropsBuilder withDrop() {
        return dropsBuilder;
    }

    @Override
    public BlockSoundsBuilder withSounds() {
        return soundsBuilder;
    }

    @Override
    public BlockEventsBuilder withEvents() {
        return eventsBuilder;
    }

    @Override
    public FluentItemBehaviour build(FluentItem fluentItem) {

        var block = new SimpleBlock(fluentItem,
                statesBuilder.build(),
                eventsBuilder.build(),
                fluentBlockSchema,
                soundsBuilder.build(),
                dropsBuilder.build(),
                displayFactory
        );

        return new BlockBehaviour(block, registry);
    }

}
