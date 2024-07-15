package immersive.handlers;

import immersive.IBConsts;
import immersive.api.ImmersiveBlock;
import immersive.api.ImmersiveBlockHandler;
import io.github.jwdeveloper.ff.core.common.java.MathUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.displays.DisplayUtils;
import io.github.jwdeveloper.ff.core.spigot.items.ItemStackUtils;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.CraftingInventory;
import org.joml.AxisAngle4f;
import org.joml.Vector2i;


public class CraftingBlock implements ImmersiveBlockHandler<CraftingInventory> {

    @Override
    public void onDestroy(ImmersiveBlock<CraftingInventory> immersiveBlock) {

    }

    @Override
    public void onLoad(ImmersiveBlock<CraftingInventory> block) {
        FluentLogger.LOGGER.info("LOAD!");

        var content = block.getMetaItemstack(IBConsts.CONTENT_TAG, 9);
        var result = block.getMetaItemstack(IBConsts.RESULT_TAG, 1);
        if (result.length != 0) {
            block.getInventory().setResult(result[0]);
        }
        block.getInventory().setMatrix(content);
        onUpdate(block);
    }

    @Override
    public void onUpdate(ImmersiveBlock<CraftingInventory> immersiveBlock) {
        FluentLogger.LOGGER.info("Update", immersiveBlock.getBlock());
        var matrix = immersiveBlock.getInventory().getMatrix();
        var playerLoc = immersiveBlock.getPlayer().getLocation();
        var blockLoc = immersiveBlock.getBlock().getLocation();
        MathUtils.getRelativeLocations(playerLoc.getDirection(), 1)
                .forEach((index, vector) ->
                {
                    var i = 8 - index;
                    var display = immersiveBlock.getDisplay(i);
                    var location = blockLoc.clone().add(vector.multiply(0.20f)).add(0.5, 1.05, 0.5);
                    display.teleport(location);
                    display.setRotation(playerLoc.getYaw(), 0);
                    updateLocation(display, i, 20);
                    if (matrix[i] != null) {
                        display.setItemStack(matrix[i].clone());
                    } else {
                        display.setItemStack(null);
                    }
                });

        var result = immersiveBlock.getDisplay(11);
        FluentApi.cache().getOrCreate("rotate", () ->
        {
            FluentApi.tasks().taskTimer(1, (iteration, task) ->
            {
                var sinus = Math.sin(Math.toRadians(iteration) * 6) / 10f;
                var tranformation = result.getTransformation();
                tranformation.getLeftRotation().set(new AxisAngle4f((float) Math.toRadians(iteration), 0.0f, 1.0f, 0.0f));
                tranformation.getTranslation().set(0, sinus, 0);
                result.setTransformation(tranformation);
            }).start();
            return true;
        });


        result.setItemStack(immersiveBlock.getInventory().getResult());
        updateResultLocation(result, immersiveBlock.getBlock());
    }

    @Override
    public void onSave(ImmersiveBlock<CraftingInventory> immersiveBlock) {
        FluentLogger.LOGGER.info("SAVING!");
        var notEmptyItemStack = ItemStackUtils.getNotEmptyItemStack(immersiveBlock.getInventory().getMatrix());
        onUpdate(immersiveBlock);
        if (notEmptyItemStack.isEmpty()) {
            immersiveBlock.remove();
            return;
        }

        var inv = immersiveBlock.getInventory();
        immersiveBlock.setItemStackMeta(IBConsts.CONTENT_TAG, inv.getMatrix());
        immersiveBlock.setItemStackMeta(IBConsts.RESULT_TAG, inv.getResult());
    }


    private void updateLocation(ItemDisplay itemDisplay, int index, double rotation) {

        var coordinates = coorinates(index, 3);
        var offset = 0.20f;
        var size = 0.10f;
        var offsetX = 0.3f;
        var offsetZ = 0.3f;
        var x = offsetX + coordinates.x * offset;
        var y = offsetZ + coordinates.y * offset;

        var transform = itemDisplay.getTransformation();
        //   transform.getLeftRotation().set(new AxisAngle4f((float) Math.toRadians(90), 1.0f, 0.0f, 0.0f));
        //  transform.getLeftRotation().set(new AxisAngle4f((float) Math.toRadians(rotation), 0, 1.0f, 0.0f));
        transform.getScale().set(size, size, size);
        //  transform.getTranslation().set(1 - x, 0.55f, 1 - y);
        itemDisplay.setTransformation(transform);
    }

    private void updateResultLocation(ItemDisplay itemDisplay, Block block) {

        itemDisplay.setInterpolationDuration(25);
        var transform = itemDisplay.getTransformation();
        transform.getScale().set(0.2f, 0.2f, 0.2f);
        itemDisplay.teleport(block.getLocation().add(0.5f, 1.5, 0.5f));
        itemDisplay.setTransformation(transform);
    }

    private Vector2i coorinates(int number, int size) {
        int gridWidth = size;
        int row = number / gridWidth;
        int col = number % gridWidth;
        return new Vector2i(row, col);
    }

    @Override
    public Class inventoryTarget() {
        return CraftingInventory.class;
    }
}
