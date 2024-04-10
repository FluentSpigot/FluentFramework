package io.github.jw.spigot.ff.example.commands;

import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.ArgumentType;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.Argument;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.Command;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;


@Command(name = "entity-test")
public class EntityTest {

    ItemDisplay itemDisplay;
    private int duration = 20;


    @Command
    public void cmd(Player player) {
        var it = getItemDisplay(player.getLocation());

        var loc = it.getLocation();
        var locPl = player.getLocation();
        locPl.setPitch(loc.getPitch());
        locPl.setYaw(loc.getYaw());
        it.teleport(locPl);


        FluentApi.messages().chat().gradient("This plugin is amazing!", Color.BLUE,Color.AQUA,Color.BLUE).send(player);
    }


    @Command(name = "summon")
    @Argument(name = "material")
    @Argument(name = "custom-model-id", argumentType = ArgumentType.INT)
    public void summon(Player player, String material, int customModelId) {
        var display = getItemDisplay(player.getLocation());
        var mat = Material.valueOf(material.toUpperCase());
        var it = new ItemStack(mat);
        var meta = it.getItemMeta();
        meta.setCustomModelData(customModelId);
        it.setItemMeta(meta);
        display.setItemStack(it);
    }




    @Command(name = "rotate")
    public void rotate(Player player) {

        getItemDisplay(player.getLocation());

    }

    @Command(name = "scale")
    @Argument(name = "factor", argumentType = ArgumentType.INT)
    public void scale(Player player, int factor) {
        var id = getItemDisplay(player.getLocation());
        var tr = id.getTransformation();

        var fac = factor / 100.0f;

        tr.getScale().set(fac);
        id.setInterpolationDuration(34);
        id.setTransformation(tr);

    }

    @Command(name = "interpolation")
    @Argument(name = "tick", argumentType = ArgumentType.INT)
    public void interpolation(Player player, int tick) {
        var id = getItemDisplay(player.getLocation());

        this.duration = tick;
    }


    private ItemDisplay getItemDisplay(Location location) {
        if (itemDisplay != null) {
            return itemDisplay;
        }

        var w = location.getWorld();
        var display = (ItemDisplay) w.spawnEntity(location, EntityType.ITEM_DISPLAY);
        this.itemDisplay = display;

        var it = new ItemStack(Material.GRASS_BLOCK);
        var meta = it.getItemMeta();
        meta.setCustomModelData(0);
        it.setItemMeta(meta);
        display.setItemStack(it);
        display.setInterpolationDuration(30);
        FluentApi.events().onEvent(PluginDisableEvent.class, pluginDisableEvent ->
        {
            display.remove();
        });
        return display;
    }
}
