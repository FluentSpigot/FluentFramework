package io.github.jwdeveloper.ff.core.spigot.commands.api.services;

import org.bukkit.command.CommandSender;

public interface MessagesService
{

    default String inactiveCommand(String commandName)
    {
        return "Sorry but "+commandName+" is inactive";
    }

    String noPermission(CommandSender sender, String permission);

    default String noAccess(CommandSender sender)
    {
        return "Sorry but "+sender.getName()+" has no access";
    }

    default String invalidArgument(String message)
    {
        return "Sorry but invalid argument "+message;
    }
}
