package io.github.jwdeveloper.ff.core.spigot.commands.api;

import io.github.jwdeveloper.ff.core.spigot.commands.implementation.SimpleCommand;
import org.bukkit.command.Command;

import java.util.Collection;
import java.util.List;

public interface FluentCommandRegistry {
    boolean register(SimpleCommand command);

    boolean unregister(SimpleCommand command);

    List<String> getBukkitCommandsNames();

    List<Command> getBukkitCommands();

    Collection<SimpleCommand> getSimpleCommands();

}
