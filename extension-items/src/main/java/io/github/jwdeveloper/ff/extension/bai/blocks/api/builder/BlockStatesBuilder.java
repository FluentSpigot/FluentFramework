package io.github.jwdeveloper.ff.extension.bai.blocks.api.builder;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.state.FluentBlockState;
import org.bukkit.Material;

import java.util.function.Consumer;

public interface BlockStatesBuilder {

    BlockStatesBuilder addDefaultState(Consumer<FluentBlockState> state);

    BlockStatesBuilder addState(String name, int customModelId);

    BlockStatesBuilder addState(FluentBlockState state);

    BlockStatesBuilder addState(Consumer<FluentBlockState> state);

    BlockStatesBuilder addState(String name, int customModelId, int index);

    BlockStatesBuilder addState(String name, int customModelId, int index, Material material);

    BlockStatesBuilder addState(String name, int customModelId, Material index);
}
