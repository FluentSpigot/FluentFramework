package io.github.jwdeveloper.ff.plugin;

import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class FluentPlugin extends JavaPlugin implements FluentApiExtension {

    public abstract void onConfiguration(FluentApiSpigotBuilder builder);

    public abstract void onFluentApiEnable(FluentApiSpigot fluentAPI);

    public abstract void onFluentApiDisabled(FluentApiSpigot fluentAPI);

    @Override
    public String getVersion() {
        return getDescription().getVersion();
    }

    @Override
    public final void onEnable() {
        var api = FluentPlugin
                .initialize(this)
                .withExtension(this)
                .create();
        if(api == null)
        {
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public static FluentPluginBuilder initialize(Plugin plugin)
    {
        return new FluentPluginBuilder(plugin);
    }
}
