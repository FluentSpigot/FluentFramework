package io.github.jwdeveloper.ff.plugin.implementation.extensions.command;

import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;

public interface FluentApiCommandBuilder extends SimpleCommandBuilder {
     SimpleCommandBuilder setName(String commandName);
     String getName();
}
