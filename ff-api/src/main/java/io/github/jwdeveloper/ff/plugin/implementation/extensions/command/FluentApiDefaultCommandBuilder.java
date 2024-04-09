package io.github.jwdeveloper.ff.plugin.implementation.extensions.command;

import io.github.jwdeveloper.ff.core.spigot.commands.api.FluentCommandRegistry;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.builder.CommandBuilderImpl;
public class FluentApiDefaultCommandBuilder extends CommandBuilderImpl implements FluentApiCommandBuilder
{
    public FluentApiDefaultCommandBuilder(String commandName, FluentCommandRegistry manger)
    {
        super(commandName, manger);
    }

    @Override
    public SimpleCommandBuilder setName(String  commandName) {
        model.setName(commandName);
        return this;
    }

    @Override
    public String  getName() {
        return model.getName();
    }
}
