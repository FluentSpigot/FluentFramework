package io.github.jwdeveloper.ff.core.spigot.commands.implementation.services;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.AccessType;
import io.github.jwdeveloper.ff.core.spigot.commands.api.models.CommandArgument;
import io.github.jwdeveloper.ff.core.spigot.commands.api.models.CommandTarget;
import io.github.jwdeveloper.ff.core.spigot.commands.api.models.ValidationResult;
import io.github.jwdeveloper.ff.core.spigot.commands.api.services.CommandService;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.SimpleCommand;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.core.spigot.permissions.implementation.PermissionsUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class CommandServiceImpl implements CommandService {

    @Override
    public boolean hasSenderAccess(CommandSender commandSender, List<AccessType> commandAccessType) {

        if (commandSender instanceof ConsoleCommandSender) {
            return true;
        }
        var result = true;
        for (var accessType : commandAccessType) {
            result = hasSenderAccess(commandSender, accessType);
        }
        return result;
    }

    @Override
    public CommandTarget isSubcommandInvoked(SimpleCommand command, String[] args) {

        if (args.length == 0 || command.getSubCommands().isEmpty()) {
            return new CommandTarget(command, args);
        }
        var arguments = command.getArguments();
        var subCommandIndex = arguments.size() + 1;

        if (subCommandIndex > args.length) {
            return new CommandTarget(command, args);
        }

        var subCommandName = args[subCommandIndex - 1];
        var subCommandOptional = command.getSubCommands()
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(subCommandName))
                .findFirst();

        if (subCommandOptional.isEmpty()) {
            return new CommandTarget(command, args);
        }
        var subCommandArgs = Arrays.copyOfRange(args, subCommandIndex, args.length);
        return isSubcommandInvoked(subCommandOptional.get(), subCommandArgs);
    }

    @Override
    public boolean hasSenderAccess(CommandSender commandSender, AccessType commandAccessType) {
        switch (commandAccessType) {
            case PLAYER -> {
                return commandSender instanceof Player;
            }
            case ENTITY -> {
                return commandSender instanceof Entity;
            }
            case CONSOLE -> {
                return commandSender instanceof ConsoleCommandSender;
            }
            case COMMAND_SENDER -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public ValidationResult hasSenderPermissions(CommandSender commandSender, List<String> permissions) {

        if (permissions.isEmpty()) {
            return new ValidationResult(true, "");
        }
        if (commandSender instanceof ConsoleCommandSender) {
            return new ValidationResult(true, "");
        }

        if (commandSender instanceof Player player) {
            if (PermissionsUtility.hasOnePermission(player, permissions)) {
                return new ValidationResult(true, "");
            }
        }
        return new ValidationResult(false, "");
    }

    public Object[] getArgumentValues(String[] args, List<CommandArgument> commandArguments) {

        if (commandArguments.isEmpty()) {
            return args;
        }
        Object[] result = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            try {
                var argument = commandArguments.get(i);
                var value = args[i];
                switch (argument.getType()) {
                    case INT -> result[i] = Integer.parseInt(value);
                    case BOOL -> result[i] = Boolean.parseBoolean(value);
                    case NUMBER -> result[i] = Float.parseFloat(value);
                    case COLOR -> result[i] = ChatColor.valueOf(value.toUpperCase());
                    case PLAYERS -> result[i] = Bukkit.getPlayer(value);
                    default -> result[i] = value;
                }
            } catch (Exception e) {
                FluentLogger.LOGGER.error("Error while getting argument ", e);
            }
        }
        return result;
    }

    @Override
    public ValidationResult validateArguments(CommandSender sender,String[] args, List<CommandArgument> commandArguments) {

        if (commandArguments.isEmpty()) {
            return new ValidationResult(true, "");
        }

        for (int i = 0; i < commandArguments.size(); i++)
        {
            var value = i < args.length ? args[i] : null;
            var argument = commandArguments.get(i);
            ValidationResult validationResult;
            for (var validator : argument.getValidators()) {
                validationResult = validator.validate(value, sender);
                if (validationResult.isFail())
                {
                    return validationResult;
                }
            }
        }
        return new ValidationResult(true, "");
    }
}
