package io.github.jwdeveloper.ff.plugin.implementation.extensions.command;

import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.CommandBuilder;

public interface FluentApiCommandBuilder extends CommandBuilder {
    public CommandBuilder setName(String commandName);

    public String getName();
}
