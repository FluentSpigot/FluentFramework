package immersive.handlers;

import immersive.IBConsts;
import immersive.api.ImmersiveBlock;
import immersive.api.ImmersiveBlockHandler;
import io.github.jwdeveloper.ff.core.common.java.MathUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import org.bukkit.entity.Display;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.EnchantingInventory;
import org.joml.AxisAngle4f;

import java.util.Random;

public class AnvilBlock implements ImmersiveBlockHandler<AnvilInventory> {
    @Override
    public void onDestroy(ImmersiveBlock<AnvilInventory> immersiveBlock) {

    }

    @Override
    public void onLoad(ImmersiveBlock<AnvilInventory> block) {
        var content = block.getMetaItemstack(IBConsts.CONTENT_TAG, 2);
        block.getInventory().setContents(content);
        onUpdate(block);
    }

    @Override
    public void onSave(ImmersiveBlock<AnvilInventory> immersiveBlock) {
        var inv = immersiveBlock.getInventory();
        immersiveBlock.setItemStackMeta(IBConsts.CONTENT_TAG, inv.getContents());
        onUpdate(immersiveBlock);
    }

    @Override
    public void onUpdate(ImmersiveBlock<AnvilInventory> immersiveBlock) {
        var matrix = immersiveBlock.getInventory().getContents();
        var blockLoc = immersiveBlock.getBlock().getLocation();


        for (var i = 0; i < matrix.length; i++) {
            var location = blockLoc.clone().add(0.5f, 1.05, 0.3 + i * 0.4f);
            var display = immersiveBlock.getDisplay(i);
            display.teleport(location);
            display.setRotation(new Random().nextInt(360), 0);
            if (matrix[i] != null) {
                setScale(display, matrix[i].getType().isBlock());
                display.setItemStack(matrix[i].clone());
            } else {
                display.setItemStack(null);
            }
        }
    }


    public void setScale(Display display, boolean block) {
        var tranfo = display.getTransformation();
        if (!block)
        {
            tranfo.getLeftRotation().set(new AxisAngle4f((float) Math.toRadians(90), 1f, 0.0f, 0.0f));
            tranfo.getScale().set(0.5, 0.5, 0.5);
        } else {
            tranfo.getScale().set(0.1, 0.1, 0.1);
        }
        display.setTransformation(tranfo);
    }

    @Override
    public Class inventoryTarget() {
        return AnvilInventory.class;
    }
}
