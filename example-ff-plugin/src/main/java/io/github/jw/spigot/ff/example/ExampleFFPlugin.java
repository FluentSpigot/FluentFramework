package io.github.jw.spigot.ff.example;

import io.github.jwdeveloper.ff.extension.bai.BlockAndItemsApi;
import io.github.jwdeveloper.ff.extension.bai.BlocksAndItemsFramework;
import io.github.jwdeveloper.ff.plugin.FluentPlugin;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExampleFFPlugin extends JavaPlugin implements FluentApiExtension {


    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        builder.useExtension(BlocksAndItemsFramework.use());
        builder.useExtension(new Extension());

    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        var bai = fluentAPI.container().findInjection(BlockAndItemsApi.class);


    }

    @Override
    public void onEnable() {
        FluentPlugin.initialize(this)
                .withExtension(this)
                .create();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


}
