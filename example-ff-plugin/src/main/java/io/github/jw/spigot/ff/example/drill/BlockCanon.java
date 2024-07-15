package io.github.jw.spigot.ff.example.drill;

import io.github.jwdeveloper.ff.core.cache.api.PlayerCache;
import io.github.jwdeveloper.ff.core.cache.api.PluginCache;
import io.github.jwdeveloper.ff.core.common.java.MathUtils;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.observer.implementation.ObserverBag;
import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItem;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemApi;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class BlockCanon {


    private PluginCache pluginCache;
    private ObserverBag<Boolean> isCanonActive = ObserverBag.create(false);


    Team sculkTeam;

    public void register(FluentApiSpigot fluentApi) {

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        sculkTeam = scoreboard.getTeam("sculkTeam");
        sculkTeam.setColor(ChatColor.RED);
        pluginCache = FluentApi.container().findInjection(PluginCache.class);
        var itemApi = fluentApi.container().findInjection(FluentItemApi.class);
        var item = itemApi.addItem()
                .withSchema(schema ->
                {
                    schema.withName("block-canon");
                    schema.withCustomModelId(1);
                    schema.withMaterial(Material.NETHERITE_SWORD);
                    schema.withDisplayName("Block canon");
                    schema.withStackable(false);
                }).buildAndRegister();

        fluentApi.events().onEvent(PlayerInteractEvent.class, event ->
        {
            handleCanon(event, item);
        });


    }

    private void handleCanon(PlayerInteractEvent playerInteractEvent, FluentItem canon) {
        var offHand = playerInteractEvent.getPlayer().getInventory().getItemInOffHand();
        var mainHand = playerInteractEvent.getPlayer().getInventory().getItemInMainHand();
        if (offHand == null || mainHand == null) {
            isCanonActive.set(false);
            return;
        }
        if (!canon.isItemStack(offHand)) {
            isCanonActive.set(false);
            return;
        }

        if (playerInteractEvent.getAction() != Action.RIGHT_CLICK_BLOCK &&
                playerInteractEvent.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }


        var player = playerInteractEvent.getPlayer();
        var playerCache = FluentApi.container().findPlayerScopeInjection(PlayerCache.class, player);
        //var display = playerCache.getOrCreate("display", () -> createDisplay(player.getLocation(), Material.GLASS));

        var selector = playerCache.getOrCreate("selector", () -> createSelectorEntity(player.getLocation()));
        var task = playerCache.getOrCreate("task", () -> canonSelectorTask(player, selector, playerCache));
        task.start();
        isCanonActive.set(true);

        var location = playerCache.getOrCreate("loc", () -> player.getLocation());

        if (player.getGameMode() == GameMode.SURVIVAL) {
            mainHand.setAmount(mainHand.getAmount() - 1);
        }
        createAnimation(player.getEyeLocation(), location, mainHand.getType());

    }

    private Entity createSelectorEntity(Location location) {
        var w = location.getWorld();
        location.setYaw(0);
        location.setPitch(0);
        var e = (Slime) w.spawnEntity(location, EntityType.SLIME);
        e.setSize(2);
        e.setAI(false);
        e.setInvisible(true);
        e.setGlowing(true);
        e.setAware(false);
        e.setInvulnerable(true);
        e.setVisualFire(false);
        e.setCollidable(false);
        e.setGliding(false);
        e.setGravity(false);
        e.setPersistent(false);
        e.setPortalCooldown(0);
        sculkTeam.addEntry(e.getUniqueId().toString());
        return e;
    }

    private void createAnimation(Location location, Location target, Material material) {
        var display = createDisplay(location, material);
        display.setGlowing(false);
        sculkTeam.addEntry(display.getUniqueId().toString());
        var trn = display.getTransformation();
        var loc = display.getLocation();
        var diff = target.clone().subtract(loc);


        loc.getWorld().spawn(loc, ItemDisplay.class, (itemDisplay) ->
        {
            Transformation transformation = itemDisplay.getTransformation();
            transformation.getScale().set(5, 5, 5);
            display.setTransformation(transformation);
        });


        trn.getTranslation().set(diff.toVector().toVector3d().add(-0.5, 0, -0.5));

        display.setInterpolationDuration(20);
        display.setInterpolationDelay(-1);
        display.setTransformation(trn);
        FluentApi.tasks().taskLater(() ->
        {
            display.remove();
            target.getBlock().setType(material);
            FluentLogger.LOGGER.info("update material", material);
        }, 21);
    }

    private SimpleTaskTimer canonSelectorTask(Player player, Entity blockDisplay, PlayerCache pluginCache) {

        var canonTask = FluentApi.tasks().taskTimer(1, (iteration, task) ->
        {
            var rayLocation = ray(player.getEyeLocation(), 30);
            var loc = rayLocation.getBlock().getLocation();
            loc.add(0.5f, 0, 0.5f);
            loc.setYaw(0);
            loc.setPitch(0);
            blockDisplay.teleport(loc);
            blockDisplay.setGlowing(true);
            pluginCache.set("loc", loc);
        });
        isCanonActive.subscribe(aBoolean ->
        {
            if (aBoolean) {
                canonTask.start();
                blockDisplay.setGlowing(true);
                //  blockDisplay.setBlock(Material.GLASS.createBlockData());
            } else {
                canonTask.stop();
                blockDisplay.setGlowing(false);
                //    blockDisplay.setBlock(Material.AIR.createBlockData());
            }

        });
        return canonTask;
    }

    private BlockDisplay createDisplay(Location location, Material material) {
        BlockDisplay blockDisplay = (BlockDisplay) location.getWorld().spawnEntity(location, EntityType.BLOCK_DISPLAY);
        blockDisplay.setBlock(material.createBlockData());
        Vector3f translation = new Vector3f(0, 0, 0);
        AxisAngle4f axisAngleRotMat = new AxisAngle4f((float) Math.PI, new Vector3f(0, 1, 0));
        Transformation transformation = new Transformation(
                translation,
                axisAngleRotMat,
                new Vector3f(1f, 1f, 1f),
                axisAngleRotMat
        );
        blockDisplay.setInterpolationDuration(25);
        blockDisplay.setTransformation(transformation);
        blockDisplay.setGlowing(true);
        //blockDisplay.setBrightness(new Display.Brightness(1, 1));

        blockDisplay.setGlowColorOverride(Color.WHITE);

        FluentApi.events().onEvent(PluginDisableEvent.class, pluginDisableEvent ->
        {
            blockDisplay.remove();
        });
        return blockDisplay;
    }

    private Location ray(Location origin, int distance) {
        Vector direction = origin.getDirection();
        Location previous = origin;
        for (int i = 0; i < distance; i++) {
            previous = origin;
            origin = origin.add(direction);
            if (origin.getBlock().getType() != Material.AIR) {
                break;
            }
        }
        return previous;
    }

    private List<Location> rayList(Location origin, int distance) {
        Vector direction = origin.getDirection();
        Location previous = origin;
        var result = new ArrayList<Location>();
        for (int i = 0; i < distance; i++) {
            previous = origin;
            origin = origin.add(direction);
            result.add(origin);
            if (origin.getBlock().getType() != Material.AIR) {
                break;
            }
        }
        return result;
    }
}
