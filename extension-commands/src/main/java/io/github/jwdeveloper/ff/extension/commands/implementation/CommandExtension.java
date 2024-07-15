package io.github.jwdeveloper.ff.extension.commands.implementation;

import io.github.jwdeveloper.ff.core.spigot.commands.SimpleCommandApi;
import io.github.jwdeveloper.ff.core.spigot.commands.api.SimpleCommandRegistry;
import io.github.jwdeveloper.ff.extension.commands.FluentCommandFramework;
import io.github.jwdeveloper.ff.extension.commands.api.CommandApi;
import io.github.jwdeveloper.ff.extension.commands.api.FluentCommandBuilderConfig;
import io.github.jwdeveloper.ff.extension.commands.api.data.FluentCommandOptions;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CommandExtension implements FluentApiExtension {
    private final Consumer<FluentCommandOptions> consumer;
    private final FluentCommandOptions options;
    private final List<CommandInvoker> invokers;
    private final List<SimpleCommandBuilder> builders;

    public CommandExtension(Consumer<FluentCommandOptions> consumer) {
        this.consumer = consumer;
        options = new FluentCommandOptions();
        invokers = new ArrayList<>();
        builders = new ArrayList<>();
    }


    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        var container = builder.container();
        container.registerSingleton(SimpleCommandRegistry.class, SimpleCommandApi.getRegistry());
        container.registerTransient(CommandFactory.class);
        container.registerSingleton(CommandApi.class, CommandApiImpl.class);


        consumer.accept(options);
        var types = options.getAllTypes();
        for (var type : types)
        {
            container.registerSingleton(type);
        }

       /* if (options.getDefaultCommand() != null) {
            var model = factory.create(options.getDefaultCommand(), builder.defaultCommand());
            invokers.addAll(model);
        }

        for (var command : options.getCommandClasses()) {
            var cmdBuilder = SimpleCommandApi.create(command.getSimpleName());
            var cmdInvokers = factory.create(command, cmdBuilder);
            invokers.addAll(cmdInvokers);
            builders.add(cmdBuilder);
        }

        for (var invoker : invokers) {
            builder.container().registerSingleton(invoker.getCommandClass());
        }*/
    }


    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {
        var api = fluentAPI.container().findInjection(FluentCommandFramework.API);
        for (var commandClassType : options.getAllTypes()) {
            SimpleCommandBuilder commandBuilder;
            if (commandClassType.equals(options.getDefaultCommand()))
            {
                var name = fluentAPI.meta().getDefaultCommandName();
                var command = api.findCommand(name).getObject();
                commandBuilder = api.createNew(commandClassType, command.edit());
            } else
            {
                commandBuilder = api.createNew(commandClassType);
            }
            var command = commandBuilder.buildAndRegister();
            if (command.getCommandModel().isDebug() && !fluentAPI.meta().isDebug()) {
                api.remove(command.getName());
            }
        }

     /*   for (var invoker : invokers) {
            var invokerInstance = fluentAPI.container().findInjection(invoker.getCommandClass());
            invoker.setCommandObject(invokerInstance);
        }

        for (var builder : builders) {
            var cmd = builder.buildAndRegister();
            if (cmd.getCommandModel().isDebug() && !fluentAPI.meta().isDebug()) {
                fluentAPI.commands().unregister(cmd);
            }
        }*/
    }
}
