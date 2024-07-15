package io.github.jw.spigot.ff.example.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import io.github.jw.spigot.ff.example.display.DisplayUpdater;
import io.github.jwdeveloper.ff.animations.api.AnimationApi;
import io.github.jwdeveloper.ff.core.common.java.MathUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.displays.DisplayUtils;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.Command;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.checkerframework.checker.units.qual.C;
import org.joml.Vector3d;
import org.joml.Vector3f;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.UUID;

@Command(name = "random-stuff")
public class RandomStuff {


    @Command(name = "spanwtest")
    public void tes2t(Player player) {
        var w = player.getLocation().getWorld();
        var e = (Interaction) w.spawnEntity(player.getLocation(), EntityType.INTERACTION);
        e.setGlowing(true);
    }


    @Command(name = "display-test")
    public void follow(Player player) {
        var pl = player.getLocation();
        var display = DisplayUtils.newItemDisplay(new ItemStack(Material.DIAMOND_BLOCK), pl);
        display.setGlowing(true);

        FluentApi.tasks().taskTimer(1, (iteration, task) ->
        {
            var loc = player.getLocation().getBlock().getLocation();
            loc.add(0.5,0.5,0.5);
            var tranfomr = display.getTransformation();
            display.setInterpolationDelay(0);
            display.setInterpolationDuration(0);
            display.setViewRange(999999999);
            tranfomr.getTranslation().set(loc.subtract(pl).toVector().toVector3d());
            display.setTransformation(tranfomr);
            FluentLogger.LOGGER.info("updating!", tranfomr.getTranslation());
        }).start();

    }

    @Command(name = "barrer-test")
    public void asdd(Player player) {
        var pl = player.getLocation();
        var display = DisplayUtils.newBlockDisplay(pl,Material.BARRIER);
        display.setGlowing(true);


        FluentLogger.LOGGER.info("siema");


    }

    @Command(name = "row")
    public void createRowOfBlocks(Player player) {
        var material = player.getInventory().getItemInMainHand();
        if (material == null) {
            material = new ItemStack(Material.DIAMOND);
        }

        ItemStack finalMaterial = material;
        MathUtils.getRelativeLocations(player.getLocation().getDirection(), 1)
                .forEach((index, vector) ->
                {
                    var id = DisplayUtils.newItemDisplay(finalMaterial, player.getLocation().clone().add(vector));
                    id.setRotation(player.getLocation().getYaw(), 0);
                });

    }

    @Command(name = "exp")
    public void exp(Player player) {
        player.setLevel(100);
    }


    @Command(name = "spawn-pig")
    public void spawnEntity(Player player) {
        var manager = FluentApi.container().findInjection(ProtocolManager.class);
        var blockBreakAnimation = manager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);
        var random = new Random();

        var loc = player.getLocation();
        blockBreakAnimation.getIntegers()
                .write(0, random.nextInt())
                .write(1, 82);
        blockBreakAnimation.getUUIDs()
                .write(0, UUID.randomUUID());

        blockBreakAnimation.getBytes()
                .write(0, (byte) 1)
                .write(1, (byte) 2)
                .write(2, (byte) 3);

        blockBreakAnimation.getDoubles()
                .write(0, loc.getX())
                .write(1, loc.getY())
                .write(2, loc.getZ());

    /*    blockBreakAnimation.getShorts()
                .write(0, (short) 1)
                .write(1, (short)1)
                .write(2, (short)1);*/

        for (var onpl : Bukkit.getOnlinePlayers()) {
            try {
                manager.sendServerPacket(onpl, blockBreakAnimation);
            } catch (InvocationTargetException e) {
                FluentLogger.LOGGER.error(e);
            }
        }
    }


    @Command(name = "entity-animation")
    public void test(Player player) {
        var itemStack = DisplayUtils.newItemDisplay(new ItemStack(Material.DIAMOND), player.getLocation());
        var api = FluentApi.container().findInjection(AnimationApi.class);

        var animation = api.createAnimation()
                .thenWait(20)
                .thenParticle(Particle.HEART)
                .thenTransform(0, 0, -10)
                .thenScale(5, 5, 5)
                .thenWait(30)
                .thenParticle(Particle.CRIT)
                .build();
        api.playAnimation(animation, itemStack);
    }

    @Command(name = "entity-circle-animation")
    public void doCirleAnimation(Player player) {
        var itemStack = DisplayUtils.newItemDisplay(new ItemStack(Material.DIAMOND), player.getLocation());
        var api = FluentApi.container().findInjection(AnimationApi.class);

        var animation = api.createAnimation();
        animation.thenScale(5, 5, 5)
                .thenWait(26);
        var steps = 20f;
        for (var i = 0; i < steps; i++) {
            var range = 10;
            var speed = 1.6f;

            var step = (i / steps) * 360;
            var radians = Math.toRadians(step * speed);
            var x = Math.cos(radians) * range;
            var y = Math.sin(radians) * range;
            var pos = new Vector(x, y, 0);
            animation.thenTransform(pos)
                    .thenScale((float) x, (float) x, (float) x)
                    .thenWait(5);

        }


   /*     api.newAnimationPlayer()
                .onStarted(fluentAnimationPlayer ->
                {
                    FluentLogger.LOGGER.info("started animation");
                })
                .onProgress(animationNode ->
                {
                    FluentLogger.LOGGER.info("current node", animationNode.getClass().getSimpleName());
                })
                .onEnded(fluentAnimationPlayer ->
                {
                    FluentLogger.LOGGER.info("ended animation");
                })
                .loop(animation.build(), itemStack);*/

    }

    @Command(name = "anim-rotate")
    public void rotateAnim(Player player) {


        var itemStack = DisplayUtils.newItemDisplay(new ItemStack(Material.DIAMOND), player.getLocation().add(0, 1, 0));
        var api = FluentApi.container().findInjection(AnimationApi.class);

        var animation = api.createAnimation();
        animation.thenWait(20);
        animation.thenTransform(-1, 0, 0);
        animation.thenRotate(180, 1, 1, 0);
        animation.thenScale(2, 2, 2);

        animation.thenWait(20 * 5);

        animation.thenRotate(0, 1, 1, 0);
        animation.thenScale(1, 1, 1);
        animation.thenWait(20 * 6);

/*
        var aplayer = api.newAnimationPlayer()
                .onStarted(fluentAnimationPlayer ->
                {
                    FluentLogger.LOGGER.info("started animation");
                })
                .onProgress(animationNode ->
                {
                    FluentLogger.LOGGER.info("current node", animationNode.getClass().getSimpleName());
                })
                .onEnded(fluentAnimationPlayer ->
                {
                    FluentLogger.LOGGER.info("ended animation");
                })
                .play(animation.build(), itemStack);
        var initLocation = player.getLocation();
        FluentApi.tasks().taskTimer(1, (iteration, task) ->
        {
            var s = Math.sin(Math.toRadians(iteration * 10)) * 2;
            itemStack.teleport(initLocation.clone().add(s, 0, 0));

            if (iteration % 100 == 0) {
                aplayer.play(animation.build(), itemStack);
            }
        }).start();*/

    }

    @Command(name = "fluent-transform")
    public void fluentUpdate(Player player) {
        var delay = 60;
        var itemStack = DisplayUtils.newItemDisplay(new ItemStack(Material.DIAMOND), player.getLocation().add(0, 1, 0));
        var updater = DisplayUpdater.create(itemStack, delay);
        FluentApi.tasks().taskTimer(delay, (iteration, task) ->
        {
            var s = Math.sin(Math.toRadians(iteration * 50)) * 2;
            updater.builder().setTranslation((float) s, 0, 0);
            updater.builder().setScale(iteration / 10.f, iteration / 10.f, iteration / 10.f);
            updater.builder().setRightRotation(iteration * 20, 1, 0, 0);
        }).start();
        updater.start();
    }

    @Command(name = "animation")
    public void asda(Player player) {
        var manager = FluentApi.container().findInjection(ProtocolManager.class);
        var blockBreakAnimation = manager.createPacket(PacketType.Play.Server.ANIMATION);


        blockBreakAnimation.getIntegers()
                .write(0, player.getEntityId())
                .write(1, 2);


        for (var onpl : Bukkit.getOnlinePlayers()) {
            try {
                manager.sendServerPacket(onpl, blockBreakAnimation);
            } catch (InvocationTargetException e) {
                FluentLogger.LOGGER.error(e);
            }
        }
    }
}
