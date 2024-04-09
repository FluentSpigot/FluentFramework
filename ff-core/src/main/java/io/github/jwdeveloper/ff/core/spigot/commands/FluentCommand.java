package io.github.jwdeveloper.ff.core.spigot.commands;

import io.github.jwdeveloper.ff.core.spigot.commands.api.FluentCommandRegistry;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.SimpleCommandManger;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.builder.CommandBuilderImpl;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

public class FluentCommand {
    private static SimpleCommandManger manger;

    public static SimpleCommandBuilder create(String name) {
        var manager = getRegistry();
        return new CommandBuilderImpl(name,manager);
    }

    public static FluentCommandRegistry getRegistry() {
        if (manger == null) {
            throw new RuntimeException("Fluent commands are disabled, use to enable it FluentCommand.enable(plugin)");
        }
        return manger;
    }
    public static FluentCommandRegistry enable(Plugin plugin) {
        if (manger != null) {
            manger.onPluginStop(new PluginDisableEvent(plugin));
        }
        manger = new SimpleCommandManger(plugin);
        return manger;
    }
}
