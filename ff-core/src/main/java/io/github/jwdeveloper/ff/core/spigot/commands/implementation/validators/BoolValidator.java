package io.github.jwdeveloper.ff.core.spigot.commands.implementation.validators;


import io.github.jwdeveloper.ff.core.spigot.commands.api.models.ValidationResult;
import org.bukkit.command.CommandSender;

public class BoolValidator implements CommandArgumentValidator{
    @Override
    public ValidationResult validate(String arg, CommandSender sender)
    {
        arg = arg.toUpperCase();
        if(arg.matches("^([T][R][U][E]|[F][A][L][S][E])$"))
         return new ValidationResult(true,"");
        else
         return new ValidationResult(false,"should be True or False");
    }
}
