package io.github.jwdeveloper.ff.core.spigot.commands.api.builder.config;

import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.BuilderConfig;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.events.CommandEvent;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.events.ConsoleCommandEvent;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.events.PlayerCommandEvent;

import java.util.function.Consumer;


public interface EventConfig extends BuilderConfig {
    EventConfig onExecute(Consumer<CommandEvent> event);

    EventConfig onPlayerExecute(Consumer<PlayerCommandEvent> event);

    EventConfig onConsoleExecute(Consumer<ConsoleCommandEvent> event);

    EventConfig onBlockExecute(Consumer<CommandEvent> event);

    EventConfig onEntityExecute(Consumer<CommandEvent> event);
}
