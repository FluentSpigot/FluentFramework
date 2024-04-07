package io.github.jwdeveloper.ff.extension.bai.blocks.api.builder;

import io.github.jwdeveloper.ff.extension.bai.common.api.BehaviourBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface BlockBuilder extends BehaviourBuilder {

    BlockBuilder withMaterial(Material material);
    BlockBuilder withCustomModelId(int id);

    BlockBuilder withHardness(int hardness);

    BlockDropsBuilder withDrop();

    BlockSoundsBuilder withSounds();

    BlockEventsBuilder withEvents();


}
