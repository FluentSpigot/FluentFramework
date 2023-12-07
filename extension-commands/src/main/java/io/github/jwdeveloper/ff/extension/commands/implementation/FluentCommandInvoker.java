package io.github.jwdeveloper.ff.extension.commands.implementation;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.commands.api.models.ValidationResult;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.events.CommandEvent;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.List;

public class FluentCommandInvoker {
    @Getter
    private final Class<?> commandClass;
    @Setter
    private Object commandObject;

    public FluentCommandInvoker(Class<?> clazz) {
        this.commandClass = clazz;
    }

    public void invoke(CommandEvent commandEvent, Method method) {
        try {
            var input = new Object[method.getParameterCount()];
            var i = -1;
            var argCounter = 0;
            for (var arg : method.getParameters()) {
                i++;
                if (arg.getType().isAssignableFrom(commandEvent.getSender().getClass())) {
                    input[i] = commandEvent.getSender();
                    continue;
                }
                if (arg.getType().isAssignableFrom(CommandEvent.class)) {
                    input[i] = commandEvent;
                    continue;
                }
                if (i > commandEvent.getArgumentCount()) {
                    break;
                }
                input[i] = commandEvent.getArgumentValue(argCounter);
                argCounter++;
            }
            method.setAccessible(true);
            method.invoke(commandObject, input);
        } catch (Exception e) {
            e.printStackTrace();

            FluentLogger.LOGGER.info(e.getMessage(), e.getCause(), e.getClass().getSimpleName());
            // throw new RuntimeException("Command Invoke error",e);
        }
    }

    public List<String> invokeOnComplete(Method method) {
        try {
            method.setAccessible(true);
            var result = method.invoke(commandObject);
            if (result == null) {
                return List.of();
            }
            return (List<String>) result;
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Unable to invoke onTabComplete at " + commandClass.getSimpleName(), e);
            return List.of();
        }
    }

    public ValidationResult invokeOnValidation(Method method, String value) {
        try {
            return (ValidationResult) method.invoke(commandObject, value);

        } catch (Exception e) {
            FluentLogger.LOGGER.error("Unable to invoke onValidation at " + commandClass.getSimpleName(), e);
            return ValidationResult.error(e.getMessage());
        }
    }
}
