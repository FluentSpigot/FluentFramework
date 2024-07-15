package io.github.jwdeveloper.ff.core.spigot.displays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;

public class DisplayUtils {
    public static ItemDisplay newItemDisplay(ItemStack itemStack, Location location) {
        var world = location.getWorld();
        var id = (ItemDisplay) world.spawnEntity(location, EntityType.ITEM_DISPLAY);
        id.setItemStack(itemStack);
        return id;
    }

    public static BlockDisplay newBlockDisplay(Location location, Material material)
    {
        var world = location.getWorld();
        var id = (BlockDisplay) world.spawnEntity(location, EntityType.BLOCK_DISPLAY);
        id.setBlock(material.createBlockData());
        return id;
    }

    public static ItemDisplay newItemDisplay(Location location) {
        var world = location.getWorld();
        var id = (ItemDisplay) world.spawnEntity(location, EntityType.ITEM_DISPLAY);
        return id;
    }

    public static TextDisplay newTextDisplay(Location location, String text) {
        var world = location.getWorld();
        var id = (TextDisplay) world.spawnEntity(location, EntityType.TEXT_DISPLAY);
        id.setText(text);
        return id;
    }
}
