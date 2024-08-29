package io.github.jw.spigot.ff.example.commands;

import io.github.jwdeveloper.ff.animations.api.AnimationApi;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.spigot.displays.DisplayUtils;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.spigot.commands.api.annotations.FCommand;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.IOException;

@FCommand(name = "animations")
public class AnimationCmd {

    private String animationPath = "D:\\Git\\fluent-framework\\example-ff-plugin\\resourcepack\\assets\\minecraft\\models\\custom\\backtools\\example_model.animation.json";
    private AnimationApi api;

    public AnimationCmd(AnimationApi api) {
        this.api = api;
    }

    @FCommand(name = "play")
    public void play(Player player) throws IOException {
        var entity = entity(player.getLocation());
        var content = FileUtility.loadFileContent(animationPath);
        var animations = api.loadFromBlockBench(content);
        var animation = animations.get(0);
        api.playAnimation(animation,entity);
    }


    public Entity entity(Location location) {
        var root = DisplayUtils.newItemDisplay(location);
        var body = DisplayUtils.newItemDisplay(new ItemStack(Material.DIAMOND_BLOCK), location);
        body.setMetadata("bone-name",new FixedMetadataValue(FluentApi.plugin(),"body"));

       /* var head = DisplayUtils.newItemDisplay(new ItemStack(Material.GRASS_BLOCK), location);
        head.setMetadata("bone-name",new FixedMetadataValue(FluentApi.plugin(),"head"));

        root.addPassenger(head);*/
        root.addPassenger(body);

        return root;
    }

}
