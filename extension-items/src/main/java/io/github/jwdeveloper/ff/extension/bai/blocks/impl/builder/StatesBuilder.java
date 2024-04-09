package io.github.jwdeveloper.ff.extension.bai.blocks.impl.builder;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockStatesBuilder;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.state.FluentBlockState;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.state.FluentBlockStates;
import org.bukkit.Material;

import java.util.function.Consumer;

public class StatesBuilder implements BlockStatesBuilder {
    private final FluentBlockStates states;

    public StatesBuilder() {
        this.states = new FluentBlockStates();
    }


    @Override
    public BlockStatesBuilder addState(FluentBlockState state) {
        states.addState(state);
        return this;
    }

    @Override
    public BlockStatesBuilder addState(Consumer<FluentBlockState> consumer) {

        var state = new FluentBlockState();
        consumer.accept(state);
        return addState(state);
    }


    @Override
    public BlockStatesBuilder addDefaultState(Consumer<FluentBlockState> consumer)
    {
        var state = new FluentBlockState();
        consumer.accept(state);
        states.setDefaultState(state);
        return this;
    }

    @Override
    public BlockStatesBuilder addState(String name, int customModelId) {
        return addState(name, customModelId, -1);
    }

    @Override
    public BlockStatesBuilder addState(String name, int customModelId, int index) {
        return addState(name, customModelId, index, Material.AIR);
    }

    @Override
    public BlockStatesBuilder addState(String name, int customModelId, int index, Material material) {
        return addState(state ->
        {
            state.setName(name);
            state.setCustomModelId(customModelId);
            state.setIndex(index);
            state.setMaterial(material);
        });
    }

    @Override
    public BlockStatesBuilder addState(String name, int customModelId, Material index) {
        return addState(name, customModelId, -1, index);
    }

    public FluentBlockStates build() {
        return states;
    }
}
