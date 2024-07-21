package io.github.jwdeveloper.ff.extension.commands.implementation;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.core.spigot.commands.api.SimpleCommandRegistry;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.builder.SimpleCommandBuilderImpl;
import io.github.jwdeveloper.ff.extension.commands.api.CommandApi;
import io.github.jwdeveloper.ff.extension.commands.api.FluentCommand;
import io.github.jwdeveloper.ff.extension.commands.api.FluentCommandBuilderConfig;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;

import java.util.List;

public class CommandApiImpl implements CommandApi {
    private final SimpleCommandRegistry commandManager;

    public CommandApiImpl(SimpleCommandRegistry simpleCommandManger) {
        this.commandManager = simpleCommandManger;
    }

    @Override
    public SimpleCommandBuilder createNew(String commandName) {
        return new SimpleCommandBuilderImpl(commandName, commandManager);
    }

    @Override
    public SimpleCommandBuilder createNew(Class<?> commandClass) {
        var builder = createNew(commandClass.getSimpleName());
        return createNew(commandClass, builder);
    }

    @Override
    public SimpleCommandBuilder createNew(Class<?> commandClass, SimpleCommandBuilder builder) {
        var factory = FluentApi.container().findInjection(CommandFactory.class);
        var invokers = factory.create(commandClass, builder);
        for (var invoker : invokers) {
            var instance = FluentApi.container().findInjection(invoker.getCommandClass());
            invoker.setCommandObject(instance);
            if (instance instanceof FluentCommandBuilderConfig config) {
                config.onBuild(invoker.getBuilder());
            }
        }
        return builder;
    }

    @Override
    public ActionResult<FluentCommand> findCommand(String uniqueName) {
        var optional = commandManager
                .getSimpleCommands()
                .stream()
                .filter(e -> e.getName().equals(uniqueName))
                .findFirst();

        if (optional.isEmpty()) {
            return ActionResult.failed("command not found");
        }
        return ActionResult.success(new CommandWrapper(optional.get(), commandManager));
    }

    @Override
    public List<FluentCommand> findAll() {
        return commandManager.getSimpleCommands()
                .stream()
                .map(simpleCommand -> (FluentCommand) new CommandWrapper(simpleCommand, commandManager))
                .toList();
    }

    @Override
    public boolean remove(FluentCommand command) {

        var wrapper = (CommandWrapper) command;
        return commandManager.unregister(wrapper.getSimpleCommand());
    }

    @Override
    public boolean remove(String name) {
        var optional = findCommand(name);
        if (optional.isFailed()) {
            return false;
        }
        var cmd = optional.getObject();
        return remove(cmd);
    }
}
