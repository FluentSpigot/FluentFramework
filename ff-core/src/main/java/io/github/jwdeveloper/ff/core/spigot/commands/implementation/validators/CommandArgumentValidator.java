package io.github.jwdeveloper.ff.core.spigot.commands.implementation.validators;

import io.github.jwdeveloper.ff.core.spigot.commands.api.models.ValidationResult;
import org.bukkit.command.CommandSender;

public interface CommandArgumentValidator
{
     ValidationResult validate(String arg, CommandSender sender);
}
