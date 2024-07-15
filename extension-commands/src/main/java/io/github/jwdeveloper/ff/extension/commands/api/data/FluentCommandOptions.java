package io.github.jwdeveloper.ff.extension.commands.api.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class FluentCommandOptions {
    private Class<?> defaultCommand;
    private final List<Class<?>> commandClasses = new ArrayList<>();

    public void setDefaultCommand(Class<?> defaultCommand) {
        this.defaultCommand = defaultCommand;
    }

    public void addCommand(Class<?> commandClazz) {
        commandClasses.add(commandClazz);
    }


    public Set<Class<?>> getAllTypes() {
        var arr = new HashSet<>(commandClasses);
        if(defaultCommand != null)
        {
            arr.add(defaultCommand);
        }
        return arr;
    }
}
