package io.github.jwdeveloper.ff.extension.commands.api;

import io.github.jwdeveloper.ff.core.common.ActionResult;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;

import java.util.List;

public interface CommandApi
{
    SimpleCommandBuilder createNew(String commandName);

    SimpleCommandBuilder createNew(Class<?> commandClass);
    SimpleCommandBuilder createNew(Class<?> commandClass, SimpleCommandBuilder builder);

    ActionResult<FluentCommand> findCommand(String uniqueName);

    List<FluentCommand> findAll();

    boolean remove(FluentCommand command);

    boolean remove(String name);
}
