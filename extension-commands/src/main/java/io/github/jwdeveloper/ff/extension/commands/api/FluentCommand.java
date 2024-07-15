package io.github.jwdeveloper.ff.extension.commands.api;

import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.api.services.EventsService;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface FluentCommand {
    String name();
    EventsService events();
    boolean execute(CommandSender sender, String commandLabel, String[] args);
    List<FluentCommand> children();
    SimpleCommandBuilder edit();
}
