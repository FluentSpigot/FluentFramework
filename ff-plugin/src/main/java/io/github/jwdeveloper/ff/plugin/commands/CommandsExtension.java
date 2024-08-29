package io.github.jwdeveloper.ff.plugin.commands;

import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

import java.util.function.Consumer;

public class CommandsExtension implements FluentApiExtension {

    Consumer<CommandsOptions> action;

    CommandsOptions options;

    public CommandsExtension(Consumer<CommandsOptions> commands) {
        this.action = commands;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        var commands = builder.commands();
        options = new CommandsOptions(commands);
        action.accept(options);

        for (var key : options.getMap().keySet()) {
            builder.container().registerSingleton(key);
        }
    }


    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {
        for (var entry : options.getMap().entrySet()) {
            var instance = fluentAPI.container().findInjection(entry.getKey());
            var builder = fluentAPI.commands().create(instance);
            entry.getValue().accept(builder);
            builder.register();
        }
    }
}
