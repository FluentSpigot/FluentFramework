package io.github.jwdeveloper.ff.plugin.implementation.extensions.command;

import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.CommandBuilder;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

public class FluentDefaultCommandExtension implements FluentApiExtension {

    private final CommandBuilder commandBuilder;

    public FluentDefaultCommandExtension(CommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        commandBuilder.buildAndRegister();
    }
}
