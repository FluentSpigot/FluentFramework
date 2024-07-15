package io.github.jwdeveloper.ff.plugin.implementation.extensions.command;

import io.github.jwdeveloper.ff.core.spigot.commands.api.SimpleCommandRegistry;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.builder.SimpleCommandBuilderImpl;
public class FluentApiDefaultCommandBuilder extends SimpleCommandBuilderImpl implements FluentApiCommandBuilder
{
    public FluentApiDefaultCommandBuilder(String commandName, SimpleCommandRegistry manger)
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
