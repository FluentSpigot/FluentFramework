package immersive.common;

import immersive.IBConsts;
import immersive.api.ImmersiveBlock;
import io.github.jwdeveloper.ff.core.cache.api.PluginCache;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.displays.DisplayUtils;
import io.github.jwdeveloper.ff.core.spigot.items.ItemStackUtils;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

@Data
public class ImmersiveBlockImpl implements ImmersiveBlock {

    private Inventory inventory;
    private Block block;
    private Player user;
    private final PluginCache pluginCache;

    public ImmersiveBlockImpl(PluginCache pluginCache) {
        this.pluginCache = pluginCache;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public Player getPlayer() {
        return user;
    }

    @Override
    public String getMetaKey(Object id) {
        var loc = block.getLocation().toVector().toString();
        var key = IBConsts.META_TAG + "_" + loc + "_" + id;
        return key;
    }

    @Override
    public ItemDisplay getDisplay(int index) {
        var metadataKey = getMetaKey(IBConsts.DISPLAY_TAG + index + "");
        var loc = block.getLocation().clone();
        loc.add(0, 0.5, 0);
        var display = pluginCache.getOrCreate(metadataKey, () -> createEntity(loc));
        if (display.isDead()) {
            display = createEntity(loc);
            pluginCache.set(metadataKey, display);
        }
        return display;
    }


    @Override
    public List<ItemDisplay> getItemDisplays() {
        return pluginCache.findAll(s -> s.startsWith(getMetaKey(IBConsts.DISPLAY_TAG)));
    }

    private ItemDisplay createEntity(Location location) {
        var itemDisplay = DisplayUtils.newItemDisplay(location);
        FluentApi.events().onEvent(PluginDisableEvent.class, pluginDisableEvent ->
        {
            itemDisplay.remove();
        });
        var size = 0.10f;
        var transform = itemDisplay.getTransformation();
        transform.getScale().set(size, size, size);
        itemDisplay.setTransformation(transform);
        itemDisplay.setInterpolationDuration(30);
        itemDisplay.setBrightness(new Display.Brightness(15, 15));
        return itemDisplay;
    }


    @Override
    public void remove() {
        getItemDisplays().forEach(Entity::remove);
        var content = getMetaItemstack(IBConsts.CONTENT_TAG);
        ItemStackUtils.dropItems(block.getLocation(), content);
        block.removeMetadata(getMetaKey(IBConsts.CONTENT_TAG), FluentApi.plugin());
        block.removeMetadata(getMetaKey(IBConsts.RESULT_TAG), FluentApi.plugin());
    }

    @Override
    public ItemStack[] getMetaItemstack(String id) {
        var key = getMetaKey(id);
        var meta = block.getMetadata(key);
        if (meta.isEmpty()) {
            return new ItemStack[0];
        }
        var content = meta.get(0).asString();
        try {
            return ItemStackUtils.itemStackArrayFromBase64(content);
        } catch (Exception e) {
            FluentLogger.LOGGER.info("Unable to read inventory content");
        }
        return new ItemStack[0];
    }

    @Override
    public ItemStack[] getMetaItemstack(String id, int defaultSize) {
        var array = getMetaItemstack(id);
        if (array.length == defaultSize) {
            return array;
        }

        var res = new ItemStack[defaultSize];
        for (var i = 0; i < res.length; i++) {
            if (i >= array.length) {
                continue;
            }
            res[i] = array[i];
        }

        return res;
    }

    @Override
    public String setItemStackMeta(String id, ItemStack... itemStacks) {
        var key = getMetaKey(id);
        var base64 = ItemStackUtils.itemStackArrayToBase64(itemStacks);
        block.setMetadata(key, new FixedMetadataValue(FluentApi.plugin(), base64));
        return key;
    }

    @Override
    public boolean isImmersiveBlock() {
        var meta = block.getMetadata(IBConsts.META_TAG);
        return !meta.isEmpty();
    }

    @Override
    public void setImerssiveBlock() {

        block.setMetadata(IBConsts.META_TAG, new FixedMetadataValue(FluentApi.plugin(), inventory.getClass().getName()));
    }


    public Class getInventoryType() {
        var meta = block.getMetadata(IBConsts.META_TAG);
        if (meta.isEmpty()) {
            return Object.class;
        }

        var value = meta.get(0).asString();
        try {
            return Class.forName(value);
        } catch (Exception e) {
            return Object.class;
        }
    }
}
