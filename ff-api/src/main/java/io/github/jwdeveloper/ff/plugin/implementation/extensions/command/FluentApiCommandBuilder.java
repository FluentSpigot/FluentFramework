package io.github.jwdeveloper.ff.api.implementation.extensions.command;

import io.github.jwdeveloper.spigot.fluent.core.spigot.commands.api.builder.CommandBuilder;

public interface FluentApiCommandBuilder extends CommandBuilder {
    public CommandBuilder setName(String commandName);

    public String getName();
}
