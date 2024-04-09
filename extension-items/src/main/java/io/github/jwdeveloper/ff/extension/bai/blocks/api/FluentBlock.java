package io.github.jwdeveloper.ff.extension.bai.blocks.api;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockEvents;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockSchema;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockSounds;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.drop.FluentBlockDrop;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.drop.FluentBlockDrops;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.state.FluentBlockStates;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface FluentBlock {
    FluentBlockInstance placeAt(Location location);
    FluentBlockInstance placeAt(Player entity, Location location);

    FluentBlockDrops drop();

    FluentItem fluentItem();

    FluentBlockSchema schema();

    FluentBlockSounds sounds();

    FluentBlockEvents events();

    FluentBlockStates states();
}
