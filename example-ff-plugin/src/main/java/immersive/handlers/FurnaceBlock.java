package immersive.handlers;

import immersive.api.ImmersiveBlock;
import immersive.api.ImmersiveBlockHandler;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.spigot.commands.impl.data.Ref;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;

import java.util.Random;

public class FurnaceBlock implements ImmersiveBlockHandler<FurnaceInventory> {


    @Override
    public void onDestroy(ImmersiveBlock<FurnaceInventory> immersiveBlock) {
        System.out.println("Furnace close!");
    }


    @Override
    public void onLoad(ImmersiveBlock<FurnaceInventory> immersiveBlock) {
        var id = immersiveBlock.getInventory().getHolder().getBlock().toString();
        if (FluentApi.cache().contains(id)) {
            var task = (SimpleTaskTimer) FluentApi.cache().get(id);
            if (task.isRunning()) {
                FluentLogger.LOGGER.info("Task already running!");
                return;
            }

        }

        var lastAmount = new Ref<Integer>(-1);
        var taskRunner = FluentApi.tasks().taskTimer(1, (iteration, task) ->
                {
                    var fuel = immersiveBlock.getInventory().getFuel();
                    if (fuel == null || fuel.getType() == Material.AIR) {
                        task.cancel();
                        return;
                    }

                    var result = immersiveBlock.getInventory().getResult();
                    if (result == null) {

                        FluentLogger.LOGGER.info("Empty Result");
                        return;
                    }

                    var currentAmount = result.getAmount();
                    if (lastAmount.getValue() == currentAmount) {
                        FluentLogger.LOGGER.info("Same amount");
                        return;
                    }
                    FluentLogger.LOGGER.info("Updated");
                    lastAmount.setValue(currentAmount);
                    onUpdate(immersiveBlock);
                })
                .onStop(taskTimer ->
                {
                    FluentLogger.LOGGER.info("Task done!");
                }).start();

        FluentApi.cache().set(id, taskRunner);
    }

    @Override
    public void onSave(ImmersiveBlock<FurnaceInventory> immersiveBlock) {

    }


    @Override
    public void onUpdate(ImmersiveBlock<FurnaceInventory> immersiveBlock) {


        var loc = immersiveBlock.getBlock().getLocation();
        var location = loc.clone().add(new Vector(1, 1, 1).multiply(0.20f)).add(0.5, 1.05, 0.5);


        var top = immersiveBlock.getDisplay(0);
        top.setItemStack(immersiveBlock.getInventory().getSmelting());
        top.teleport(location.clone().add(-0.7, -0.6, -0.20));
        var tranformation = top.getTransformation();
        tranformation.getScale().set(0.2f, 0.2f, 0.2f);
        tranformation.getLeftRotation().set(new AxisAngle4f((float) Math.toRadians(90), 0, 1f, 0f));
        top.setTransformation(tranformation);

        var bottom = immersiveBlock.getDisplay(1);
        bottom.setItemStack(immersiveBlock.getInventory().getFuel());
        bottom.teleport(location.clone().add(-0.7, -1.1, -0.20));
        tranformation = bottom.getTransformation();
        tranformation.getScale().set(0.3f, 0.3f, 0.3f);
        tranformation.getLeftRotation().set(new AxisAngle4f((float) Math.toRadians(90), 0, 1f, 0f));
        bottom.setTransformation(tranformation);


        var resultItemStack = immersiveBlock.getInventory().getResult();
        if (resultItemStack == null) {
            return;
        }
        if (resultItemStack.getType().isAir()) {
            return;
        }

        var random = new Random();
        var offset = 2;
        var baseLoc = immersiveBlock.getInventory().getHolder().getBlock().getLocation();
        for (var i = offset; i < resultItemStack.getAmount(); i++) {
            var item = immersiveBlock.getDisplay(i);
            var angle = random.nextFloat() * (float) (2 * Math.PI);
            var radius = random.nextFloat();
            var r = Math.sqrt(radius) * 0.4;
            var xx = r * Math.cos(angle);
            var zz = r * Math.sin(angle);
            var x = xx + 0.5f;
            var z = zz + 0.5f;
            var y = -r;


            var targetLoc = baseLoc.clone().add(x, 1.2 + y, z);

            tranformation = item.getTransformation();
            tranformation.getScale().set(0.2f, 0.2f, 0.2f);
            tranformation.getLeftRotation().set(new AxisAngle4f((float) Math.toRadians(90), 1, 0f, 0f));
            item.setTransformation(tranformation);

            item.teleport(targetLoc);
            item.setItemStack(resultItemStack);
        }
    }

    @Override
    public Class inventoryTarget() {
        return FurnaceInventory.class;
    }
}
