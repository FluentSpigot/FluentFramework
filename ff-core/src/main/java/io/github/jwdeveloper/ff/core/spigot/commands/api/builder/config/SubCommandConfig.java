package io.github.jwdeveloper.ff.core.spigot.commands.api.builder.config;

import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.BuilderConfig;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.SimpleCommand;

import java.util.List;
import java.util.function.Consumer;

public interface SubCommandConfig extends BuilderConfig
{
    SubCommandConfig addSubCommand(SimpleCommandBuilder builder);

    SubCommandConfig addSubCommand(SimpleCommand simpleCommand);

    SubCommandConfig addSubCommand(List<SimpleCommand> simpleCommand);

    SubCommandConfig addSubCommand(String name, Consumer<SimpleCommandBuilder> config);

}
