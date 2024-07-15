package io.github.jw.spigot.ff.example.commands;

import io.github.jwdeveloper.ff.animations.api.AnimationApi;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.extension.commands.api.annotations.Command;
import io.github.jwdeveloper.ff.models.api.DisplayModelApi;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import org.bukkit.entity.Player;

import java.io.IOException;

@Command(name = "display-test")
public class DisplayEntity {


    private final String file = "D:\\Git\\fluent-framework\\example-ff-plugin\\resourcepack\\assets\\minecraft\\models\\custom\\backtools\\example_model.geo.json";
    private String animationPath = "D:\\Git\\fluent-framework\\example-ff-plugin\\resourcepack\\assets\\minecraft\\models\\custom\\backtools\\example_model.animation.json";

    @Command(name = "spawn")
    public void entity(Player player) throws IOException {
        var content = FileUtility.loadFileContent(file);
        var api = FluentApi.container().findInjection(DisplayModelApi.class);
        var model = api.loadFromBlockBench(content);
        var display = model.getObject().spawn(player.getLocation());


    }

    @Command(name = "spawn-anim")
    public void entityAnim(Player player) throws IOException {
        var content = FileUtility.loadFileContent(file);
        var api = FluentApi.container().findInjection(DisplayModelApi.class);
        var model = api.loadFromBlockBench(content);
        var display = model.getObject().spawn(player.getLocation());

        var animApi = FluentApi.container().findInjection(AnimationApi.class);
        var animContent = FileUtility.loadFileContent(animationPath);
        var animations = animApi.loadFromBlockBench(animContent);
        var animation = animations.get(0);
        display.playAnimation(animation);
    }
}
