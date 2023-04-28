package io.github.jwdeveloper.ff.core.spigot.commands.api.builder.config;

import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.BuilderConfig;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.CommandBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.SimpleCommand;

import java.util.List;
import java.util.function.Consumer;

public interface SubCommandConfig extends BuilderConfig
{
    SubCommandConfig addSubCommand(CommandBuilder builder);

    SubCommandConfig addSubCommand(SimpleCommand simpleCommand);

    SubCommandConfig addSubCommand(List<SimpleCommand> simpleCommand);

    SubCommandConfig addSubCommand(String name, Consumer<CommandBuilder> config);

}
