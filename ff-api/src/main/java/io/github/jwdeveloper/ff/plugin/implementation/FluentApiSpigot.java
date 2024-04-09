package io.github.jwdeveloper.ff.plugin.implementation;

import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.spigot.commands.api.FluentCommandRegistry;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.builder.CommandBuilderImpl;
import io.github.jwdeveloper.ff.core.spigot.events.api.FluentEventManager;
import io.github.jwdeveloper.ff.core.spigot.messages.FluentMessages;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.core.validator.api.FluentValidator;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtensionsManager;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.FluentInjection;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.mediator.FluentMediator;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api.FluentPermission;
import org.bukkit.plugin.Plugin;

public final class FluentApiSpigot {

    private final FluentInjection fluentInjection;
    private final FluentMediator fluentMediator;
    private final FluentConfig fluentConfig;
    private final FluentPermission fluentPermission;
    private final Plugin plugin;
    private final FluentMessages simpleMessages;
    private final FluentApiMeta fluentApiMeta;
    private final FluentEventManager fluentEvents;
    private final FluentTaskFactory simpleTasks;
    private final FluentCommandRegistry commandManger;
    private final FluentApiExtensionsManager extensionsManager;
    private final PluginLogger logger;

    private final FluentValidator validator;

    public FluentApiSpigot(
            Plugin plugin,
            FluentInjection injection,
            FluentMediator mediator,
            FluentConfig fluentConfig,
            FluentPermission permission,
            FluentApiExtensionsManager extensionsManager,
            PluginLogger logger,
            FluentCommandRegistry commandManger,
            FluentEventManager eventManager,
            FluentTaskFactory taskManager,
            FluentApiMeta fluentApiMeta,
            FluentMessages fluentMessages,
            FluentValidator validator) {
        this.plugin = plugin;
        this.fluentConfig = fluentConfig;
        this.extensionsManager = extensionsManager;
        this.commandManger = commandManger;
        this.logger = logger;
        fluentInjection = injection;
        fluentMediator = mediator;
        fluentPermission = permission;
        fluentEvents = eventManager;
        simpleTasks = taskManager;
        simpleMessages = fluentMessages;
        this.fluentApiMeta = fluentApiMeta;
        this.validator = validator;
    }

    public void enable() {
        extensionsManager.onEnable(this);
    }

    public void disable() {
        extensionsManager.onDisable(this);
    }

    public FluentPermission permission() {
        return fluentPermission;
    }

    public FluentInjection container() {
        return fluentInjection;
    }

    public FluentMediator mediator() {
        return fluentMediator;
    }

    public FluentConfig config() {
        return fluentConfig;
    }

    public FluentEventManager events() {
        return fluentEvents;
    }

    public FluentTaskFactory tasks() {
        return simpleTasks;
    }

    public FluentCommandRegistry commands() {
        return commandManger;
    }

    public SimpleCommandBuilder createCommand(String commandName) {
        return new CommandBuilderImpl(commandName, commandManger);
    }

    public FluentMessages messages() {
        return simpleMessages;
    }

    public Plugin plugin() {
        return plugin;
    }

    public PluginLogger logger() {
        return logger;
    }

    public String path() {
        return FileUtility.pluginPath(plugin);
    }

    public String dataPath() {
        return path() + FileUtility.separator() + "data";
    }
    public FluentApiMeta meta() { return fluentApiMeta;}

    public FluentValidator validator()
    {
        return validator;
    }
}

