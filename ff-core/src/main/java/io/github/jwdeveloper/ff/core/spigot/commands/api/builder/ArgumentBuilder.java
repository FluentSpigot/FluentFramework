package io.github.jwdeveloper.ff.core.spigot.commands.api.builder;

import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.ArgumentDisplay;
import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.ArgumentType;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.validators.CommandArgumentValidator;

import java.util.List;
import java.util.function.Supplier;

public interface ArgumentBuilder
{
    ArgumentBuilder setType(ArgumentType type);

    ArgumentBuilder setTabComplete(Supplier<List<String>> tabComplete);

    ArgumentBuilder setTabComplete(String tabComplete);

    ArgumentBuilder setTabComplete(String tabComplete, int index);

    ArgumentBuilder setValidator(CommandArgumentValidator validator);

    ArgumentBuilder setArgumentDisplay(ArgumentDisplay argumentDisplayMode);

    ArgumentBuilder setDescription(String description);

}
