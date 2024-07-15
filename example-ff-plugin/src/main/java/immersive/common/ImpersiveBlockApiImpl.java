package immersive.common;

import immersive.api.ImmersiveBlockApi;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class ImpersiveBlockApiImpl implements ImmersiveBlockApi
{

    @Override
    public ItemStack[] loadItemStacks(Block block, String metaId) {
        return new ItemStack[0];
    }

    @Override
    public String base64(ItemStack... itemStacks) {
        return null;
    }

    @Override
    public String getMetaId(Block block) {
        return null;
    }

    @Override
    public void setMeta(Block block, String metaName, String metaContent) {

    }

    @Override
    public void clearMeta(Block block) {

    }
}
