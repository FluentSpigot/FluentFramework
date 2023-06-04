package io.github.jwdeveloper.ff.core.spigot.commands.api.builder;

import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.config.ArgumentConfig;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.config.EventConfig;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.config.PropertiesConfig;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.config.SubCommandConfig;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.SimpleCommand;

import java.util.function.Consumer;

public interface SimpleCommandBuilder
{
    SimpleCommandBuilder propertiesConfig(Consumer<PropertiesConfig> config);
    SimpleCommandBuilder eventsConfig(Consumer<EventConfig> config);
    SimpleCommandBuilder argumentsConfig(Consumer<ArgumentConfig> config);
    SimpleCommandBuilder subCommandsConfig(Consumer<SubCommandConfig> config);
    SimpleCommand buildAndRegister();
    SimpleCommand build();
}
