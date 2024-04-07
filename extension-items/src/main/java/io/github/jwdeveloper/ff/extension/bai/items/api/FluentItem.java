package io.github.jwdeveloper.ff.extension.bai.items.api;

import io.github.jwdeveloper.ff.extension.bai.common.api.FluentItemBehaviour;
import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemSchema;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

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
    boolean canPlayerUse(Player player);

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

    void addBehaviour(FluentItemBehaviour itemBehaviour);
    void removeBehaviour(FluentItemBehaviour behaviour);
    void addBehaviour(Set<FluentItemBehaviour> behaviours);
}
