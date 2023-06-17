package io.github.jwdeveloper.ff.core.spigot.commands.implementation.builder;

import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.ArgumentBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.ArgumentDisplay;
import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.ArgumentType;
import io.github.jwdeveloper.ff.core.spigot.commands.api.models.CommandArgument;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.validators.*;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ArgumentBuilderImpl implements ArgumentBuilder {

    private final CommandArgument argument;

    public ArgumentBuilderImpl(String argumentName) {
        argument = new CommandArgument();
        argument.setName(argumentName);
    }

    @Override
    public ArgumentBuilder setType(ArgumentType type) {
        switch (type) {
            case INT -> argument.addValidator(new IntValidator());
            case NUMBER, FLOAT -> argument.addValidator(new FloatValidator());
            case BOOL -> {
                argument.addValidator(new BoolValidator());
                argument.setTabCompleter(()->List.of("true", "false"));
            }
            case COLOR -> {
                argument.addValidator(new ColorValidator());
                argument.setTabCompleter(() ->Arrays.stream(ChatColor.values()).toList().stream().map(c -> c.name()).toList());
            }
            case PLAYERS -> {
                argument.setArgumentDisplayMode(ArgumentDisplay.TAB_COMPLETE);
            }
        }
        argument.setType(type);
        return this;
    }

    @Override
    public ArgumentBuilder setTabComplete(Supplier<List<String>> tabComplete) {
        argument.setTabCompleter(tabComplete);
        return this;
    }

    @Override
    public ArgumentBuilder setTabComplete(String tabComplete) {
        var tabCompleter = argument.getTabCompleter();
      //  tabCompleter.add(tabComplete);
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    @Override
    public ArgumentBuilder setTabComplete(String tabComplete, int index) {
        var tabCompleter = argument.getTabCompleter();
        throw new RuntimeException("NOT IMPLEMENTED");
      //  tabCompleter.set(index, tabComplete);

    }

    @Override
    public ArgumentBuilder setValidator(CommandArgumentValidator validator) {
        argument.getValidators().add(validator);
        return this;
    }

    @Override
    public ArgumentBuilder setArgumentDisplay(ArgumentDisplay argumentDisplayMode) {
        argument.setArgumentDisplayMode(argumentDisplayMode);
        return this;
    }

    @Override
    public ArgumentBuilder setDescription(String description) {
        argument.setDescription(description);
        return this;
    }

    public CommandArgument build() {
        return argument;
    }
}
