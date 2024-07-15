package immersive.api;

import org.bukkit.block.Block;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ImmersiveBlock<T extends Inventory> {
    T getInventory();

    Block getBlock();
    Player getPlayer();

    String getMetaKey(Object id);
    ItemDisplay getDisplay(int index);

    List<ItemDisplay> getItemDisplays();

    void remove();

    ItemStack[] getMetaItemstack(String id);
    ItemStack[] getMetaItemstack(String id, int defaultSize);

    String setItemStackMeta(String id, ItemStack... itemStacks);

    boolean isImmersiveBlock();

    void setImerssiveBlock();
}
