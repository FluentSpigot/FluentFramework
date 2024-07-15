package io.github.jwdeveloper.ff.core.spigot.commands;

import io.github.jwdeveloper.ff.core.spigot.commands.api.SimpleCommandRegistry;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.SimpleCommandRegistryImpl;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.builder.SimpleCommandBuilderImpl;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

public class SimpleCommandApi {
    private static SimpleCommandRegistryImpl manger;

    public static SimpleCommandBuilder create(String name) {
        var manager = getRegistry();
        return new SimpleCommandBuilderImpl(name,manager);
    }

    public static SimpleCommandRegistry getRegistry() {
        if (manger == null) {
            throw new RuntimeException("Fluent commands are disabled, use to enable it FluentCommand.enable(plugin)");
        }
        return manger;
    }
    public static SimpleCommandRegistry enable(Plugin plugin) {
        if (manger != null) {
            manger.onPluginStop(new PluginDisableEvent(plugin));
        }
        manger = new SimpleCommandRegistryImpl(plugin);
        return manger;
    }
}
