package io.github.jwdeveloper.ff.core.spigot.commands.implementation.validators;

import io.github.jwdeveloper.ff.core.spigot.commands.api.models.ValidationResult;
import org.bukkit.command.CommandSender;

public class FloatValidator implements CommandArgumentValidator {
    @Override
    public ValidationResult validate(String arg, CommandSender sender) {
        if(arg.matches("[+-]?([0-9]*[.])?[0-9]+"))
            return new ValidationResult(true,"");
        else
            return new ValidationResult(false,"should be number");
    }
}
