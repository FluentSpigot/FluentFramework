package io.github.jwdeveloper.ff.extension.items.api;

import io.github.jwdeveloper.ff.core.mapper.validators.ValidationResult;
import io.github.jwdeveloper.ff.extension.items.api.crafting.FluentCrafting;
import io.github.jwdeveloper.ff.extension.items.api.schema.FluentItemSchema;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface FluentItem {


    /**
     * @return create new instance of itemstack
     */
    ItemStack toItemStack();


    /**
     * @return checks if itemstack is created from this FluentItem
     */
    boolean isItemStack(ItemStack itemStack);

    /**
     * checks if player has permissions to use this item
     *
     * @param player
     * @return
     */
    boolean isPlayerCanUse(Player player);

    /**
     * Trying to give item to player inventory. If inventory is full
     * item is drop under player
     *
     * @param player
     * @return
     */
    ItemStack give(Player... player);

    /**
     * Drops item at the selected location
     *
     * @param location
     * @return
     */
    ItemStack drop(Location location);

    /**
     * Manager events of item
     *
     * @return
     */
    FluentItemEvents events();

    /**
     * @return item metadata and properties
     */
    FluentItemSchema getSchema();

}
