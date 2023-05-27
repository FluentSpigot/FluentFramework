package io.github.jwdeveloper.ff.plugin.api;

import io.github.jwdeveloper.ff.core.common.logger.BukkitLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.ff.plugin.api.assembly_scanner.JarScanner;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.command.FluentApiCommandBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api.FluentPermissionBuilder;
import org.bukkit.plugin.Plugin;

import java.nio.file.Path;


public interface FluentApiSpigotBuilder {
    FluentApiCommandBuilder defaultCommand();

    FluentApiContainerBuilder container();

    FluentApiSpigotBuilder useExtension(FluentApiExtension extension);

    FluentConfig config();

    FluentPermissionBuilder permissions();

    Plugin plugin();

    Path pluginPath();

    JarScanner jarScanner();

    BukkitLogger logger();

    FluentTaskManager tasks();
}
