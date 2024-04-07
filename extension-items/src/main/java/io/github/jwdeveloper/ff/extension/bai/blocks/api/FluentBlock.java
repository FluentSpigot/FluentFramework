package io.github.jwdeveloper.ff.extension.bai.blocks.api;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockEvents;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockSchema;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.data.FluentBlockSounds;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public interface FluentBlock {
    FluentBlockInstance spawnAt(Location location);
    ItemStack[] drop(Entity entity, ItemStack itemStack);

    FluentItem fluentItem();

    FluentBlockSchema schema();

    FluentBlockSounds sounds();

    FluentBlockEvents events();
}
