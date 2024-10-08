package io.github.jwdeveloper.ff.plugin.implementation;

import io.github.jwdeveloper.dependance.api.JarScanner;
import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.plugin.api.FluentApiContainerBuilder;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.api.logger.LoggerConfiguration;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api.FluentPermissionBuilder;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.spigot.commands.api.Commands;
import io.github.jwdeveloper.spigot.commands.api.builder.CommandBuilder;
import org.bukkit.plugin.Plugin;

import java.nio.file.Path;


public class FluentApiBuilder implements FluentApiSpigotBuilder {

    public static FluentApiBuilder create(Plugin plugin, FluentApiExtension extension) {
        var builder = create(plugin);
        builder.useExtension(extension);
        return builder;
    }

    public static FluentApiBuilder create(Plugin plugin) {
        return new FluentApiBuilder(new FluentApiSpigotBuilderImpl(plugin));
    }

    private final FluentApiSpigotBuilderImpl builder;

    FluentApiBuilder(FluentApiSpigotBuilderImpl builder) {
        this.builder = builder;
    }

    @Override
    public CommandBuilder mainCommand() {
        return builder.mainCommand();
    }

    @Override
    public Commands commands() {
        return builder.commands();
    }

    @Override
    public FluentApiContainerBuilder container() {
        return builder.container();
    }

    @Override
    public FluentApiSpigotBuilder useExtension(FluentApiExtension extension) {
        return builder.useExtension(extension);
    }

    @Override
    public FluentApiSpigotBuilder bindToConfig(Class<?> clazz, String ymlPath) {
        return builder.bindToConfig(clazz, ymlPath);
    }

    @Override
    public FluentApiSpigotBuilder bindToConfig(Class<?> clazz) {
        return builder.bindToConfig(clazz);
    }

    @Override
    public FluentConfig config() {
        return builder.config();
    }

    @Override
    public FluentPermissionBuilder permissions() {
        return builder.permissions();
    }

    @Override
    public Plugin plugin() {
        return builder.plugin();
    }

    @Override
    public Path pluginPath() {
        return builder.pluginPath();
    }

    @Override
    public JarScanner jarScanner() {
        return builder.jarScanner();
    }

    @Override
    public PluginLogger logger() {
        return builder.logger();
    }

    @Override
    public LoggerConfiguration loggerConfiguration() {
        return builder.loggerConfiguration();
    }

    @Override
    public FluentTaskFactory tasks() {
        return builder.tasks();
    }

    @Override
    public FluentApiMeta meta() {
        return builder.meta();
    }



    public FluentApiSpigot build() throws Exception {
        var api = builder.build();
        FluentApi.setFluentApiSpigot(api);
        return api;
    }
}
