package io.github.jwdeveloper.extensions.commands.api;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FluentCommandOptions
{
    private Class<?> defaultCommand;
    private List<Class> commandClasses = new ArrayList<>();

    public void setDefaultCommand(Class<?> defaultCommand)
    {
         this.defaultCommand = defaultCommand;
    }

    public void addCommand(Class<?> commandClazz)
    {
        commandClasses.add(commandClazz);
    }
}
