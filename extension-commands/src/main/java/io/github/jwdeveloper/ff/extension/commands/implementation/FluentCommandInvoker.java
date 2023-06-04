package io.github.jwdeveloper.ff.extension.commands.implementation;
import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.events.CommandEvent;
import lombok.Getter;
import lombok.Setter;
import java.lang.reflect.Method;

public class FluentCommandInvoker
{
    @Getter
    private final Class<?> commandClass;
    @Setter
    private Object commandObject;

    public FluentCommandInvoker(Class<?> clazz) {
        this.commandClass = clazz;
    }

    public void invoke(CommandEvent commandEvent, Method method)
    {
        try
        {
            var input = new Object[method.getParameterCount()];
            var i =-1;
            var argCounter = 0;
            for(var arg : method.getParameters())
            {
                i++;
                if(arg.getType().isAssignableFrom(commandEvent.getSender().getClass()))
                {
                    input[i] = commandEvent.getSender();
                    continue;
                }
                if(arg.getType().isAssignableFrom(CommandEvent.class))
                {
                    input[i] = commandEvent;
                    continue;
                }
                if(i > commandEvent.getArgumentCount())
                {
                    break;
                }
                input[i] = commandEvent.getArgumentValue(argCounter);
                argCounter ++;
            }
            method.invoke(commandObject, input);
        }
        catch (Exception e)
        {
            e.printStackTrace();

            FluentLogger.LOGGER.info(  e.getMessage(), e.getCause(), e.getClass().getSimpleName());
           // throw new RuntimeException("Command Invoke error",e);
        }
    }
}
