package io.github.jwdeveloper.ff.plugin.api;

import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.dependance.api.JarScanner;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.api.logger.LoggerConfiguration;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiMeta;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.command.FluentApiCommandBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api.FluentPermissionBuilder;
import org.bukkit.plugin.Plugin;

import java.nio.file.Path;


public interface FluentApiSpigotBuilder {
    FluentApiCommandBuilder defaultCommand();

    FluentApiContainerBuilder container();

    FluentApiSpigotBuilder useExtension(FluentApiExtension extension);

    FluentApiSpigotBuilder bindToConfig(Class<?> clazz, String ymlPath);

    FluentApiSpigotBuilder bindToConfig(Class<?> clazz);

    FluentConfig config();

    FluentPermissionBuilder permissions();

    Plugin plugin();

    Path pluginPath();

    JarScanner jarScanner();

    PluginLogger logger();

    LoggerConfiguration loggerConfiguration();

    FluentTaskFactory tasks();

    FluentApiMeta meta();
}
