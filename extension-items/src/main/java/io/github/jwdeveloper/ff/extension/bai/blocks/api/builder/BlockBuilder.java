package io.github.jwdeveloper.ff.extension.bai.blocks.api.builder;

import io.github.jwdeveloper.ff.extension.bai.common.api.BehaviourBuilder;
import org.bukkit.Material;

public interface BlockBuilder extends BehaviourBuilder {

    BlockBuilder withHardness(int hardness);

    BlockStatesBuilder withStates();

    BlockDropsBuilder withDrop();

    BlockSoundsBuilder withSounds();

    BlockEventsBuilder withEvents();




}
