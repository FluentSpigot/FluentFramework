package immersive.api;

import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;

public interface ImmersiveBlockHandler<T extends Inventory> {

    void onDestroy(ImmersiveBlock<T> immersiveBlock);
    void onLoad(ImmersiveBlock<T> immersiveBlock);

    void onSave(ImmersiveBlock<T> immersiveBlock);

    void onUpdate(ImmersiveBlock<T> immersiveBlock);

    Class inventoryTarget();
}
