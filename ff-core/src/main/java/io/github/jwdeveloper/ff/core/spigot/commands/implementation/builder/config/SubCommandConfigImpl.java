package io.github.jwdeveloper.ff.core.spigot.commands.implementation.builder.config;

import io.github.jwdeveloper.ff.core.spigot.commands.api.SimpleCommandRegistry;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.config.SubCommandConfig;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.SimpleCommand;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.builder.SimpleCommandBuilderImpl;

import java.util.List;
import java.util.function.Consumer;

public class SubCommandConfigImpl implements SubCommandConfig {

    private final List<SimpleCommand> commands;
    private final SimpleCommandRegistry manger;

    public SubCommandConfigImpl(List<SimpleCommand> commands, SimpleCommandRegistry manger) {
        this.commands = commands;
        this.manger = manger;
    }

    @Override
    public SubCommandConfig addSubCommand(SimpleCommandBuilder builder) {
        return addSubCommand(builder.build());
    }

    @Override
    public SubCommandConfig addSubCommand(SimpleCommand simpleCommand) {
        commands.add(simpleCommand);
        return this;
    }

    @Override
    public SubCommandConfig addSubCommand(List<SimpleCommand> simpleCommand) {
        commands.addAll(simpleCommand);
        return this;
    }

    @Override
    public SubCommandConfig addSubCommand(String name, Consumer<SimpleCommandBuilder> config) {
        var builder = new SimpleCommandBuilderImpl(name,  manger);
        config.accept(builder);
        return addSubCommand(builder.build());
    }
}
