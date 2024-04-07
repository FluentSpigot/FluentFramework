package io.github.jwdeveloper.ff.plugin.implementation;

import com.google.gson.Gson;
import io.github.jwdeveloper.dependance.implementation.common.JarScannerImpl;
import io.github.jwdeveloper.dependance.implementation.common.JarScannerOptions;
import io.github.jwdeveloper.ff.core.cache.api.PluginCache;
import io.github.jwdeveloper.ff.core.cache.implementation.PluginCacheImpl;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.json.JsonUtility;
import io.github.jwdeveloper.ff.plugin.api.FluentApiContainerBuilder;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLogger;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.core.spigot.commands.FluentCommand;
import io.github.jwdeveloper.ff.core.spigot.commands.api.FluentCommandManger;
import io.github.jwdeveloper.ff.core.spigot.events.FluentEvent;
import io.github.jwdeveloper.ff.core.spigot.events.api.FluentEventManager;
import io.github.jwdeveloper.ff.core.spigot.messages.FluentMessages;
import io.github.jwdeveloper.ff.core.spigot.tasks.FluentTask;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskFactory;
import io.github.jwdeveloper.ff.core.validator.api.FluentValidator;
import io.github.jwdeveloper.ff.core.validator.implementation.FluentValidatorImpl;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.dependance.api.JarScanner;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPriority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.config.FluentConfigLoader;
import io.github.jwdeveloper.ff.plugin.implementation.config.FluentConfigManager;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.FluentApiExtentionsManagerImpl;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.command.FluentApiCommandBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.command.FluentApiDefaultCommandBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.command.FluentDefaultCommandExtension;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.FluentInjectionImpl;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.mediator.FluentMediator;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.mediator.fluentMediatorExtension;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api.FluentPermission;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api.FluentPermissionBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.implementation.FluentPermissionBuilderImpl;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.implementation.FluentPermissionExtension;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.player.PlayerContainerBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.listeners.ChatInputListener;
import io.github.jwdeveloper.ff.plugin.api.logger.LoggerConfiguration;
import io.github.jwdeveloper.ff.plugin.implementation.logger.LoggerConfigurationImpl;
import lombok.SneakyThrows;
import org.bukkit.plugin.Plugin;

import java.io.Closeable;
import java.nio.file.Path;

public class FluentApiSpigotBuilderImpl implements FluentApiSpigotBuilder {
    private final io.github.jwdeveloper.ff.plugin.api.FluentApiContainerBuilder containerBuilder;
    private final FluentApiDefaultCommandBuilder defaultCommandBuilder;
    private final FluentApiExtentionsManagerImpl extensionsManager;
    private final FluentPermissionBuilderImpl fluentPermissionBuilder;
    private final Plugin plugin;
    private final JarScanner jarScanner;
    private final PluginLogger logger;
    private final FluentTaskFactory taskFactory;
    private final FluentCommandManger commandManger;
    private final FluentEventManager eventManager;
    private final LoggerConfigurationImpl loggerConfiguration;
    private final FluentConfigManager configManager;
    private final FluentApiMeta fluentApiMeta;

    private final PlayerContainerBuilder playerContainerBuilder;


    @Override
    public FluentApiCommandBuilder defaultCommand() {
        return defaultCommandBuilder;
    }

    @Override
    public io.github.jwdeveloper.ff.plugin.api.FluentApiContainerBuilder container() {
        return containerBuilder;
    }

    @Override
    public FluentConfig config() {
        return configManager.getConfig();
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
        return jarScanner;
    }

    @Override
    public PluginLogger logger() {
        return logger;
    }

    @Override
    public LoggerConfiguration loggerConfiguration() {
        return loggerConfiguration;
    }

    @Override
    public FluentTaskFactory tasks() {
        return taskFactory;
    }

    @Override
    public FluentApiMeta meta() {
        return fluentApiMeta;
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

    @SneakyThrows
    public FluentApiSpigotBuilderImpl(Plugin plugin) {
        this.plugin = plugin;
        var config = new FluentConfigLoader(plugin).load();

        logger = FluentLogger.setLogger(plugin.getName());
        commandManger = FluentCommand.enable(plugin);
        eventManager = FluentEvent.enable(plugin);
        taskFactory = FluentTask.enable(plugin);


        var jarScannerOptions = new JarScannerOptions();
        jarScannerOptions.setRootPackage(plugin.getClass());
        jarScanner = new JarScannerImpl(jarScannerOptions, plugin.getLogger());
        containerBuilder = new FluentApiContainerBuilder();


        extensionsManager = new FluentApiExtentionsManagerImpl(logger);
        defaultCommandBuilder = new FluentApiDefaultCommandBuilder(plugin.getName(), commandManger);
        fluentApiMeta = new FluentApiMeta(config, plugin, defaultCommandBuilder);
        fluentPermissionBuilder = new FluentPermissionBuilderImpl(plugin);
        configManager = new FluentConfigManager(jarScanner, logger, config);
        loggerConfiguration = new LoggerConfigurationImpl(logger, plugin);
        playerContainerBuilder = new PlayerContainerBuilder();
    }


    public FluentApiSpigot build() throws Exception {

        extensionsManager.registerLow(new FluentPermissionExtension(fluentPermissionBuilder));
        extensionsManager.registerLow(new fluentMediatorExtension());
        extensionsManager.register(new FluentDefaultCommandExtension(defaultCommandBuilder), ExtentionPriority.HIGH);


        extensionsManager.getBeforeEachOnConfigure().subscribe(configManager::onMigration);
        extensionsManager.getAfterOnConfigure().subscribe(configManager::handleRegisterBindings);
        extensionsManager.getBeforeOnEnable().subscribe(configManager::handleClassMappingFromFile);
        extensionsManager.getAfterOnEnable().subscribe(configManager::onSaveConfig);
        extensionsManager.getAfterOnDisable().subscribe(e ->
        {
            var factory = (SimpleTaskFactory) taskFactory;
            factory.close();
            for (var closable : e.container().findAllByInterface(Closeable.class)) {
                try {
                    closable.close();
                } catch (Exception ex) {
                }
            }
        });
        extensionsManager.onConfiguration(this);
        containerBuilder.registerSingleton(Plugin.class, plugin);
        containerBuilder.registerSingleton(FluentConfig.class, configManager.getConfig());
        containerBuilder.registerSingleton(FluentTaskFactory.class, taskFactory);
        containerBuilder.registerSingleton(FluentEventManager.class, eventManager);
        containerBuilder.registerSingleton(FluentCommandManger.class, commandManger);
        containerBuilder.registerSingleton(PluginLogger.class, loggerConfiguration.getPluginLogger());
        containerBuilder.registerSingleton(PlayerLogger.class, loggerConfiguration.getPlayerLogger());
        containerBuilder.registerSingleton(FluentApiMeta.class, fluentApiMeta);
        containerBuilder.registerSingleton(JarScanner.class, jarScanner);
        containerBuilder.registerSingleton(FluentMessages.class, new FluentMessages());
        containerBuilder.registerSingleton(FluentValidator.class, new FluentValidatorImpl());
        containerBuilder.registerSingleton(Gson.class, JsonUtility.getGson());
        containerBuilder.registerSingleton(PluginCache.class, PluginCacheImpl.class);
        containerBuilder.registerSingleton(ChatInputListener.class);
        containerBuilder.scan(jarScanner);
        containerBuilder.configure(config ->
        {
            config.onInjection(configManager::onConfigOptionsInjectionCall);
            config.onRegistration(playerContainerBuilder::handleRegistrationEvent);
        });

        var container = containerBuilder.build();
        var playerContainer = playerContainerBuilder.build(container);
        var injection = new FluentInjectionImpl(container, playerContainer);

        final var mediator = injection.findInjection(FluentMediator.class);
        final var permissions = injection.findInjection(FluentPermission.class);
        final var messages = injection.findInjection(FluentMessages.class);
        final var validator = injection.findInjection(FluentValidator.class);
        final var config = configManager.getConfig();
        return new FluentApiSpigot(
                plugin,
                injection,
                mediator,
                config,
                permissions,
                extensionsManager,
                logger,
                commandManger,
                eventManager,
                taskFactory,
                fluentApiMeta,
                messages,
                validator);
    }


         /* final var injectionFactory = new FluentInjectionFactory(containerBuilder, logger, plugin, jarScanner);
        final var factoryResult = injectionFactory.create();
        final var injection = factoryResult.fluentInjection();
        extensionsManager.getBeforeOnEnable().subscribe(apiSpigot ->
        {
            for (var toInitializeClasses : factoryResult.toInitializeTypes()) {
                injection.findInjection(toInitializeClasses);
            }
        });*/

}
