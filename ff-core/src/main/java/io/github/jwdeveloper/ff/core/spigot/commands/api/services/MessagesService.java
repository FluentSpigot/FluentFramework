package io.github.jwdeveloper.ff.core.spigot.commands.api.services;

import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import org.bukkit.ChatColor;
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

    default String invalidArgument()
    {
        return new MessageBuilder()
                .text("Argument")
                .space()
                //.inBrackets((i + 1) + "")
                .space()
                .color(ChatColor.GREEN)
                .color(ChatColor.BOLD)
              //  .inBrackets(argument.getName())
                .space()
                .color(ChatColor.WHITE)
              //  .text(message)
                .toString();
    }
}
