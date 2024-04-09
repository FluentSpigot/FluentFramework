package io.github.jwdeveloper.ff.core.spigot.commands.implementation.builder;

import io.github.jwdeveloper.ff.core.spigot.commands.api.FluentCommandRegistry;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.BuilderConfig;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.config.ArgumentConfig;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.config.EventConfig;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.config.PropertiesConfig;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.config.SubCommandConfig;
import io.github.jwdeveloper.ff.core.spigot.commands.api.models.CommandModel;
import io.github.jwdeveloper.ff.core.spigot.commands.api.services.CommandService;
import io.github.jwdeveloper.ff.core.spigot.commands.api.services.EventsService;
import io.github.jwdeveloper.ff.core.spigot.commands.api.services.MessagesService;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.SimpleCommand;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.builder.config.ArgumentConfigImpl;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.builder.config.EventConfigImpl;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.builder.config.PropertiesConfigImpl;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.builder.config.SubCommandConfigImpl;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.services.CommandServiceImpl;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.services.EventsServiceImpl;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.services.MessageServiceImpl;

import java.util.*;
import java.util.function.Consumer;

public class CommandBuilderImpl implements SimpleCommandBuilder {
    protected final EventsService eventsService;
    protected final CommandService commandService;
    protected final MessagesService messagesService;
    protected final List<SimpleCommand> subCommands;

    protected final CommandModel model;
    private final Map<Consumer, BuilderConfig> configs;
    private final FluentCommandRegistry manger;

    public CommandBuilderImpl(String commandName,
                              FluentCommandRegistry manger) {
        configs = new LinkedHashMap<>();
        eventsService = new EventsServiceImpl();
        commandService = new CommandServiceImpl();
        messagesService = new MessageServiceImpl();
        subCommands = new ArrayList<>();
        model = new CommandModel();
        model.setName(commandName);
        this.manger = manger;
    }


    @Override
    public SimpleCommandBuilder propertiesConfig(Consumer<PropertiesConfig> config) {
        configs.put(config, new PropertiesConfigImpl(model));
        return this;
    }

    @Override
    public SimpleCommandBuilder eventsConfig(Consumer<EventConfig> consumer) {
        configs.put(consumer, new EventConfigImpl(eventsService));
        return this;
    }

    @Override
    public SimpleCommandBuilder argumentsConfig(Consumer<ArgumentConfig> config) {
        configs.put(config, new ArgumentConfigImpl(model));
        return this;
    }

    @Override
    public SimpleCommandBuilder subCommandsConfig(Consumer<SubCommandConfig> config) {
        configs.put(config, new SubCommandConfigImpl(subCommands, manger));
        return this;
    }

    @Override
    public SimpleCommand build() {
        for (var configurationSet : configs.entrySet()) {
            configurationSet.getKey().accept(configurationSet.getValue());
        }
        return new SimpleCommand(
                model,
                subCommands,
                commandService,
                messagesService,
                eventsService);
    }

    public SimpleCommand buildAndRegister() {
        var result = build();
        manger.register(result);
        return result;
    }
}
