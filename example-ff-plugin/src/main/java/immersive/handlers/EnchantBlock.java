package immersive.handlers;

import immersive.IBConsts;
import immersive.api.ImmersiveBlock;
import immersive.api.ImmersiveBlockHandler;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import it.unimi.dsi.fastutil.Hash;
import org.bukkit.block.Block;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.joml.AxisAngle4f;

import java.util.*;

public class EnchantBlock implements ImmersiveBlockHandler<EnchantingInventory> {

    private final static String ROTATION_ENCHANT = "rotate-enchant";
    private final static String ROTATION_LAPIS = "rotate-lapis";

    @Override
    public void onDestroy(ImmersiveBlock<EnchantingInventory> block) {
        FluentApi.cache().remove(block.getMetaKey(ROTATION_ENCHANT));
        FluentApi.cache().remove(block.getMetaKey(ROTATION_LAPIS));
    }

    @Override
    public void onLoad(ImmersiveBlock<EnchantingInventory> block) {
        var content = block.getMetaItemstack(IBConsts.CONTENT_TAG, 2);
        block.getInventory().setContents(content);
        onUpdate(block);
    }

    @Override
    public void onSave(ImmersiveBlock<EnchantingInventory> immersiveBlock) {
        var inv = immersiveBlock.getInventory();
        immersiveBlock.setItemStackMeta(IBConsts.CONTENT_TAG, inv.getContents());
        onUpdate(immersiveBlock);
    }

    @Override
    public void onUpdate(ImmersiveBlock<EnchantingInventory> immersiveBlock) {
        handleItem(immersiveBlock);
        handleLapis(immersiveBlock);
    }


    private void handleItem(ImmersiveBlock<EnchantingInventory> immersiveBlock) {
        var enchating = immersiveBlock.getInventory().getItem();
        var enchantDisplay = immersiveBlock.getDisplay(0);
        if (enchating == null) {
            enchantDisplay.setItemStack(null);
            return;
        }

        var display = immersiveBlock.getDisplay(0);
        display.setItemStack(enchating.clone());
        updateResultLocation(display, immersiveBlock.getBlock());
        var key = immersiveBlock.getMetaKey(ROTATION_ENCHANT);
        FluentApi.cache().getOrCreate(key, () ->
                FluentApi.tasks().taskTimer(1, (iteration, task) ->
                {
                    var sinus = Math.sin(Math.toRadians(iteration) * 6) / 10f;
                    var tranformation = display.getTransformation();
                    tranformation.getLeftRotation().set(new AxisAngle4f((float) Math.toRadians(iteration), 0.0f, 1.0f, 0.0f));
                    tranformation.getTranslation().set(0, sinus, 0);
                    display.setTransformation(tranformation);
                }).start());
    }

    List<ItemDisplay> avaliable = new ArrayList<ItemDisplay>();

    private void handleLapis(ImmersiveBlock<EnchantingInventory> immersiveBlock) {
        var lapis = immersiveBlock.getInventory().getSecondary();
        var maxCount = 64;

        avaliable.clear();
        ;
        for (var i = 1; i <= maxCount; i++) {
            var display = immersiveBlock.getDisplay(i);
            if (lapis == null) {
                display.setItemStack(null);
                continue;
            }
            var lapisCount = lapis.getAmount();
            if (i <= lapisCount) {
                display.setItemStack(lapis);
                avaliable.add(display);
            } else {
                display.setItemStack(null);
            }
        }

        if (lapis == null) {
            return;
        }

        var key = immersiveBlock.getMetaKey(ROTATION_LAPIS);
        FluentApi.cache().getOrCreate(key, () ->
                {
                    return FluentApi.tasks().taskTimer(1, (iteration, task) ->
                    {
                        for (var i = 1; i <= avaliable.size(); i++) {
                            var display = avaliable.get(i - 1);
                            FluentLogger.LOGGER.info("avaliable size", i);

                            double angleOffset = 360f / avaliable.size();
                            double x = 0.4;
                            double y = 0.4;
                            double speed = 5;
                            double angleDegrees = (iteration + angleOffset * i) * speed;
                            double angleRadians = Math.toRadians(angleDegrees);
                            double newX = x * Math.cos(angleRadians) - y * Math.sin(angleRadians);
                            double newY = x * Math.sin(angleRadians) + y * Math.cos(angleRadians);

                            double a = Math.cos(angleRadians);
                            double b = Math.sin(angleRadians);

                            var sinus = Math.sin(Math.toRadians(iteration + angleOffset) * 5) / 5f;

                            var tranformation = display.getTransformation();

                            tranformation.getTranslation().set(newX + 0.5, 0.5 + sinus, newY + 0.5);
                            tranformation.getRightRotation().set(new AxisAngle4f((float) Math.toRadians(45), (float) a, 0, (float) b));
                            //tranformation.getRightRotation().set(new AxisAngle4f((float) Math.toRadians(45), 1, 0, 0));
                            display.setTransformation(tranformation);
                            setScale(display);
                        }
                    }).start();
                }
        );
    }

    private void updateResultLocation(ItemDisplay itemDisplay, Block block) {

        itemDisplay.setInterpolationDuration(25);
        var transform = itemDisplay.getTransformation();
        transform.getScale().set(0.4f, 0.4f, 0.4f);
        itemDisplay.teleport(block.getLocation().add(0.5f, 1.5, 0.5f));
        itemDisplay.setTransformation(transform);
    }


    public void setScale(Display display) {
        var tranfo = display.getTransformation();
        tranfo.getLeftRotation().set(new AxisAngle4f((float) Math.toRadians(90), 1f, 0.0f, 0.0f));
        tranfo.getScale().set(0.3, 0.3, 0.3);
        display.setTransformation(tranfo);
    }


    @Override
    public Class inventoryTarget() {
        return EnchantingInventory.class;
    }
}
