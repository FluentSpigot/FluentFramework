package io.github.jwdeveloper.ff.extension.commands.implementation;

import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.api.services.EventsService;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.SimpleCommand;
import io.github.jwdeveloper.ff.extension.commands.api.FluentCommand;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandWrapper implements FluentCommand {

    @Getter
    private final SimpleCommand simpleCommand;

    public CommandWrapper(SimpleCommand simpleCommand) {
        this.simpleCommand = simpleCommand;
    }


    public String name()
    {
        return simpleCommand.getName();
    }

    public EventsService events() {
        return simpleCommand.getEventsService();
    }

    public final boolean execute(CommandSender sender, String commandLabel, String[] args)
    {
        return simpleCommand.execute(sender,commandLabel,args);
    }


    public List<FluentCommand> children()
    {
        return simpleCommand.getSubCommands().stream().map(e ->
        {
            var child = new CommandWrapper(e);
            return (FluentCommand)child;
        }).toList();
    }

    @Override
    public SimpleCommandBuilder edit() {
        return null;
    }
}
