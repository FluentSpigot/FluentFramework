package io.github.jwdeveloper.extensions.commands.implementation;

import io.github.jwdeveloper.extensions.commands.api.FluentCommandOptions;
import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.commands.FluentCommand;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FluentCommandExtension implements FluentApiExtension {
    private final Consumer<FluentCommandOptions> consumer;
    private FluentCommandOptions options;
    private FluentCommandFactory factory;
    private Map<Class<?>, FluentCommandInvoker> invokers;
    private List<SimpleCommandBuilder> builders;

    public FluentCommandExtension(Consumer<FluentCommandOptions> consumer) {
        this.consumer = consumer;
        options = new FluentCommandOptions();
        factory = new FluentCommandFactory();
        invokers = new HashMap<>();
        builders = new ArrayList<>();
    }


    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        consumer.accept(options);

        if(options.getDefaultCommand() != null)
        {
            var model = factory.create(options.getDefaultCommand(), builder.defaultCommand());
            invokers.put(options.getDefaultCommand(), model);
            builder.container().registerSigleton(options.getDefaultCommand());
        }

        for (var command : options.getCommandClasses()) {
            var cmdBuilder = FluentCommand.create(command.getSimpleName());
            var cmdModel = factory.create(command, cmdBuilder);
            invokers.put(command, cmdModel);
            builders.add(cmdBuilder);
            builder.container().registerSigleton(command);
        }
    }


    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {
        for (var invoker : invokers.entrySet()) {
            var invokerInstance = fluentAPI.container().findInjection(invoker.getKey());
            invoker.getValue().setTarget(invokerInstance);
        }

        for(var builder : builders)
        {
            builder.buildAndRegister();
        }
    }
}
