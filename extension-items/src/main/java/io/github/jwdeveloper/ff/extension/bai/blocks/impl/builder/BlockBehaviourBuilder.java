package io.github.jwdeveloper.ff.extension.bai.blocks.impl.builder;

import io.github.jwdeveloper.ff.extension.bai.blocks.BlockBehaviour;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockRegistry;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockBuilder;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockDropsBuilder;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockEventsBuilder;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockSoundsBuilder;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockSchema;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.DisplayFactory;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.SimpleBlock;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.SimpleBlockRegistry;
import io.github.jwdeveloper.ff.extension.bai.common.api.FluentItemBehaviour;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import org.bukkit.Material;

public class BlockBehaviourBuilder implements BlockBuilder {

    private final DropsBuilder dropsBuilder;
    private final EventsBuilder eventsBuilder;
    private final SoundsBuilder soundsBuilder;
    private final FluentBlockSchema fluentBlockSchema;
    private final DisplayFactory displayFactory;
    private final FluentBlockRegistry registry;


    public BlockBehaviourBuilder(FluentBlockRegistry simpleBlockRegistry, DisplayFactory displayFactory) {
        dropsBuilder = new DropsBuilder();
        eventsBuilder = new EventsBuilder();
        soundsBuilder = new SoundsBuilder();
        this.fluentBlockSchema = new FluentBlockSchema();
        this.displayFactory = displayFactory;
        this.registry = simpleBlockRegistry;
    }

    @Override
    public BlockBuilder withMaterial(Material material) {
        fluentBlockSchema.setMaterial(material);
        return this;
    }

    @Override
    public BlockBuilder withCustomModelId(int id) {
        fluentBlockSchema.setCustomModelId(id);
        return this;
    }

    @Override
    public BlockBuilder withHardness(int hardness) {
        fluentBlockSchema.setHardness(hardness);
        return this;
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
                eventsBuilder.build(),
                fluentBlockSchema,
                soundsBuilder.build(),
                dropsBuilder.build(),
                displayFactory
        );

        return new BlockBehaviour(block, registry);
    }

}
