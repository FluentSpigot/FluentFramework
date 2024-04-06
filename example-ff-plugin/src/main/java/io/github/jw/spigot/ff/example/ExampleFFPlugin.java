package io.github.jw.spigot.ff.example;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.plugin.FluentPlugin;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExampleFFPlugin extends JavaPlugin implements FluentApiExtension {


    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

        var clazz = fluentAPI.container().findInjection(ExampleConfig.class);
        var exampleString = fluentAPI.container().findInjection(String.class);
        FluentLogger.LOGGER.info("Example string has value", exampleString);
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
