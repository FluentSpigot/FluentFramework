package immersive.api;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public interface ImmersiveBlockApi {
    ItemStack[] loadItemStacks(Block block, String metaId);

    String base64(ItemStack... itemStacks);

    String getMetaId(Block block);

    void setMeta(Block block, String metaName, String metaContent);

    void clearMeta(Block block);
}
