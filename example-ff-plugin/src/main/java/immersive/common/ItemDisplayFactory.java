package immersive.common;

import immersive.IBConsts;
import io.github.jwdeveloper.ff.core.cache.api.PluginCache;
import io.github.jwdeveloper.ff.core.spigot.displays.DisplayUtils;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemDisplay;

public class ItemDisplayFactory {
    private final PluginCache pluginCache;

    public ItemDisplayFactory(PluginCache pluginCache) {
        this.pluginCache = pluginCache;
    }

    public ItemDisplay getItemDisplay(Block block, Object identifier) {
        return pluginCache.getOrCreate(cacheKey(block, identifier), () ->
        {
            return DisplayUtils.newItemDisplay(block.getLocation());
        });
    }


    private String cacheKey(Block block, Object identifier) {
        var vec = block.getLocation().toVector().toString();
        return IBConsts.META_TAG + vec + identifier;
    }
}
