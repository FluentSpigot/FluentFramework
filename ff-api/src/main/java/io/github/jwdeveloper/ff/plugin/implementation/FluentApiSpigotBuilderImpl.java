package io.github.jwdeveloper.ff.plugin.implementation;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.common.logger.PluginLogger;
import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.commands.FluentCommand;
import io.github.jwdeveloper.ff.core.spigot.commands.api.FluentCommandManger;
import io.github.jwdeveloper.ff.core.spigot.events.FluentEvent;
import io.github.jwdeveloper.ff.core.spigot.events.api.FluentEventManager;
import io.github.jwdeveloper.ff.core.spigot.tasks.FluentTask;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.ff.plugin.api.FluentApiContainerBuilder;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.assembly_scanner.JarScanner;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPriority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.assemby_scanner.JarScannerImpl;
import io.github.jwdeveloper.ff.plugin.implementation.config.FluentConfigManager;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.FluentApiExtentionsManagerImpl;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.command.FluentApiCommandBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.command.FluentApiDefaultCommandBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.command.FluentDefaultCommandExtension;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.FluentInjectionFactory;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.decorator.FluentDecorator;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.mediator.FluentMediator;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.mediator.FluentMediatorExtention;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api.FluentPermission;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api.FluentPermissionBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.implementation.FluentPermissionBuilderImpl;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.implementation.FluentPermissionExtention;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;

import java.nio.file.Path;

public class FluentApiSpigotBuilderImpl implements FluentApiSpigotBuilder {
    private final FluentApiContainerBuilderImpl containerBuilder;
    private final FluentApiDefaultCommandBuilder commandBuilder;
    private final FluentApiExtentionsManagerImpl extensionsManager;
    private final FluentPermissionBuilderImpl fluentPermissionBuilder;
    private final FluentConfig configFile;
    private final Plugin plugin;
    private final JarScannerImpl assemblyScanner;
    private final PluginLogger logger;
    private final FluentTaskManager taskManager;
    private final FluentCommandManger commandManger;
    private final FluentEventManager eventManager;
    private final FluentConfigManager configManager;

    @SneakyThrows
    public FluentApiSpigotBuilderImpl(Plugin plugin) {
        this.plugin = plugin;
        logger = FluentLogger.setLogger(plugin.getLogger());
        commandManger = FluentCommand.enable(plugin);
        eventManager = FluentEvent.enable(plugin);
        taskManager = FluentTask.enable(plugin);

        extensionsManager = new FluentApiExtentionsManagerImpl(logger);
        containerBuilder = new FluentApiContainerBuilderImpl(extensionsManager, logger, FluentDecorator.CreateDecorator());
        commandBuilder = new FluentApiDefaultCommandBuilder(plugin.getName(), commandManger);
        fluentPermissionBuilder = new FluentPermissionBuilderImpl(plugin);
        assemblyScanner = new JarScannerImpl(plugin, logger);
        configManager = new FluentConfigManager(assemblyScanner, logger, plugin);
        configFile = configManager.getConfig();
    }

    @Override
    public FluentApiCommandBuilder defaultCommand() {
        return commandBuilder;
    }

    @Override
    public FluentApiContainerBuilder container() {
        return containerBuilder;
    }

    @Override
    public FluentConfig config() {
        return configFile;
    }

    @Override
    public FluentPermissionBuilder permissions() {
        return fluentPermissionBuilder;
    }

    @Override
    public Plugin plugin() {
        return plugin;
    }

    @Override
    public Path pluginPath() {
        return plugin.getDataFolder().toPath();
    }

    @Override
    public JarScanner jarScanner() {
        return assemblyScanner;
    }

    @Override
    public PluginLogger logger() {
        return logger;
    }

    @Override
    public FluentTaskManager tasks() {
        return taskManager;
    }

    @Override
    public FluentApiSpigotBuilder useExtension(FluentApiExtension extension) {
        extensionsManager.register(extension, extension.getPriority());
        return this;
    }

    @Override
    public FluentApiSpigotBuilder bindToConfig(Class<?> clazz, String ymlPath) {
        configManager.bindToConfig(clazz, ymlPath);
        return this;
    }

    @Override
    public FluentApiSpigotBuilder bindToConfig(Class<?> clazz) {
        return bindToConfig(clazz, StringUtils.EMPTY);
    }

    public FluentApiSpigot build() throws Exception {

        extensionsManager.registerLow(new FluentPermissionExtention(fluentPermissionBuilder));
        extensionsManager.registerLow(new FluentMediatorExtention());
        extensionsManager.register(new FluentDefaultCommandExtension(commandBuilder), ExtentionPriority.HIGH);

        extensionsManager.getBeforeEachOnConfigure().subscribe(configManager::handleMigration);
        extensionsManager.getAfterOnConfigure().subscribe(configManager::handleRegisterBindings);
        extensionsManager.getBeforeOnEnable().subscribe(configManager::handleClassMappingFromFile);

        extensionsManager.getAfterOnEnable().subscribe(configManager::handleSaveConfig);
        extensionsManager.getAfterOnDisable().subscribe(configManager::handleSaveConfig);

        extensionsManager.onConfiguration(this);

        containerBuilder.getConfiguration().onInjection(configManager::handleConfigInjection);

        containerBuilder.registerSigleton(Plugin.class, plugin);
        containerBuilder.registerSigleton(FluentConfig.class, configFile);
        containerBuilder.registerSigleton(FluentTaskManager.class, taskManager);
        containerBuilder.registerSigleton(FluentEventManager.class, eventManager);
        containerBuilder.registerSigleton(FluentCommandManger.class, commandManger);
        containerBuilder.registerSigleton(PluginLogger.class, logger);
        containerBuilder.registerSigleton(JarScanner.class, assemblyScanner);
        final var injectionFactory = new FluentInjectionFactory(containerBuilder, logger, plugin, assemblyScanner);
        final var factoryResult = injectionFactory.create();
        final var injection = factoryResult.fluentInjection();
        extensionsManager.getBeforeOnEnable().subscribe(apiSpigot ->
        {
            for (var toInitializeClasses : factoryResult.toInitializeTypes()) {
                injection.findInjection(toInitializeClasses);
            }
        });

        final var mediator = injection.findInjection(FluentMediator.class);
        final var permissions = injection.findInjection(FluentPermission.class);

        return new FluentApiSpigot(
                plugin,
                injection,
                mediator,
                configFile,
                permissions,
                extensionsManager,
                logger,
                commandManger,
                eventManager,
                taskManager);
    }
}
