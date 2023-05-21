package io.github.jwdeveloper.ff.plugin.api;

import io.github.jwdeveloper.ff.plugin.api.assembly_scanner.FluentAssemblyScanner;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.command.FluentApiCommandBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.documentation.DocumentationOptions;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api.FluentPermissionBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.resourcepack.ResourcepackOptions;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.updater.api.UpdaterApiOptions;
import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskManager;
import org.bukkit.plugin.Plugin;

import java.nio.file.Path;
import java.util.function.Consumer;

public interface FluentApiSpigotBuilder {
    FluentApiCommandBuilder defaultCommand();

    FluentApiContainerBuilder container();

    FluentApiSpigotBuilder useExtension(FluentApiExtension extension);

    FluentApiSpigotBuilder useMetrics(int metricsId);

    FluentApiSpigotBuilder useUpdater(Consumer<UpdaterApiOptions> options);

    FluentApiSpigotBuilder useDocumentation(Consumer<DocumentationOptions> options);

    FluentApiSpigotBuilder useDocumentation();

    FluentApiSpigotBuilder useResourcePack(Consumer<ResourcepackOptions> options);

    FluentConfig config();

    FluentPermissionBuilder permissions();

    Plugin plugin();
    Path pluginPath();

    FluentAssemblyScanner classFinder();

    SimpleLogger logger();

    FluentTaskManager tasks();
}
