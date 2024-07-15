package immersive;

import immersive.api.ImmersiveBlock;
import immersive.api.ImmersiveBlockHandler;
import immersive.common.ImmersiveBlockImpl;
import io.github.jwdeveloper.ff.core.cache.api.PluginCache;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.items.ItemStackUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.joml.Vector2i;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BlocksHandler {
    private final List<ImmersiveBlockHandler> immersiveBlocks;
    private final PluginCache pluginCache;

    public BlocksHandler(List<ImmersiveBlockHandler> immersiveBlocks, PluginCache pluginCache) {
        this.immersiveBlocks = immersiveBlocks;
        this.pluginCache = pluginCache;
    }

    public void handleDestroy(Block block)
    {
        var immersiveBlock = new ImmersiveBlockImpl(pluginCache);
        immersiveBlock.setBlock(block);
        if(!immersiveBlock.isImmersiveBlock())
        {
            return;
        }

        var inventoryType = immersiveBlock.getInventoryType();
        getBlockHandler(inventoryType).ifPresent(handler ->
        {
            handler.onDestroy(immersiveBlock);
        });

        immersiveBlock.remove();
    }

    public void handleOpen(Block block, Inventory inventory, Player holder) {
        getBlockHandler(inventory.getClass()).ifPresent(handler ->
        {
            var immsersiveBlock = getBlockInstance(block, inventory, holder);
            immsersiveBlock.setImerssiveBlock();
            handler.onLoad(immsersiveBlock);
        });
    }

    public void handleUpdate(Block block, Inventory inventory, Player holder) {
        getBlockHandler(inventory.getClass()).ifPresent(handler ->
        {
            var immsersiveBlock = getBlockInstance(block, inventory, holder);
            if(!immsersiveBlock.isImmersiveBlock())
            {
               return;
            }
            handler.onUpdate(immsersiveBlock);
        });
    }

    public void handleClose(Block block, Inventory inventory, Player holder) {
        getBlockHandler(inventory.getClass()).ifPresent(handler ->
        {
            var immsersiveBlock = getBlockInstance(block, inventory, holder);
            if(!immsersiveBlock.isImmersiveBlock())
            {
                return;
            }
            handler.onSave(immsersiveBlock);
        });
    }

    private ImmersiveBlock getBlockInstance(Block block, Inventory inventory, Player holder) {
        var impl = new ImmersiveBlockImpl(pluginCache);
        impl.setBlock(block);
        impl.setInventory(inventory);
        impl.setUser(holder);
        return impl;
    }

    private Optional<ImmersiveBlockHandler> getBlockHandler(Class<?> invenotryType) {
        var optional = immersiveBlocks
                .stream()
                .filter(e -> e.inventoryTarget().isAssignableFrom(invenotryType))
                .findFirst();
        return optional;
    }


    /*  private void createMatrixDisplays(CraftingInventory craftingInventory) {
          var location = craftingInventory.getLocation().add(0, 0.5, 0);
          var content = craftingInventory.getMatrix();
          var index = 0;
          for (var itemStack : content) {
              var itemDisplay = (ItemDisplay) pluginCache.getOrCreate(getCacheId(location, index), () ->
              {
                  var display = factory.createDisplay(location, itemStack);
                  FluentApi.events().onEvent(PluginDisableEvent.class, pluginDisableEvent ->
                  {
                      display.remove();
                  });
                  return display;
              });
              var coordinates = coorinates(index, 3);
              var scaleFactor = 0.2f;
              var offsetX = 0.3f;
              var offsetZ = 0.3f;
              var x = offsetX + coordinates.x * scaleFactor;
              var y = offsetZ + coordinates.y * scaleFactor;
              var transform = itemDisplay.getTransformation();
              transform.getLeftRotation().set(new AxisAngle4f((float) Math.toRadians(90), 1.0f, 0.0f, 0.0f));
              transform.getScale().set(scaleFactor / 2, scaleFactor / 2, scaleFactor / 2);
              transform.getTranslation().set(1 - x, 0.5f, 1 - y);
              itemDisplay.setTransformation(transform);
              itemDisplay.setItemStack(itemStack);
              itemDisplay.setBrightness(new Display.Brightness(15, 15));
              index++;
          }
      }

      private void createOutputDisplay(CraftingInventory craftingInventory) {
          var result = craftingInventory.getResult();
          var location = craftingInventory.getLocation().clone().add(0, 1, 0);
          var itemDisplay = (ItemDisplay) pluginCache.getOrCreate(getCacheId(location, 11), () ->
          {
              var display = factory.createDisplay(location, result);
              FluentApi.events().onEvent(PluginDisableEvent.class, pluginDisableEvent ->
              {
                  display.remove();
              });
              FluentApi.tasks().taskTimer(30, (iteration, task) ->
              {
                  if (display.isDead()) {
                      task.stop();
                      return;
                  }
                  var transform = display.getTransformation();
                  transform.getLeftRotation().set(new AxisAngle4f((float) Math.toRadians(iteration * 90), 0, 1.0f, 0.0f));
                  display.setTransformation(transform);
              }).start();
              return display;
          });

          itemDisplay.setInterpolationDuration(25);

          var transform = itemDisplay.getTransformation();

          transform.getScale().set(0.3f, 0.3f, 0.3f);
          itemDisplay.setTransformation(transform);

          itemDisplay.setBrightness(new Display.Brightness(15, 15));
          itemDisplay.setItemStack(result);
      }

  */





}
