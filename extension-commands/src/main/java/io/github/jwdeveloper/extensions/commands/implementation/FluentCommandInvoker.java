package io.github.jwdeveloper.extensions.commands.implementation;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.events.CommandEvent;
import lombok.Setter;
import java.lang.reflect.Method;

public class FluentCommandInvoker
{
    private final Class<?> clazz;
    @Setter
    private Object target;

    public FluentCommandInvoker(Class<?> clazz) {
        this.clazz = clazz;
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


            method.invoke(target, input);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Command Invoke error",e);
        }
    }
}
