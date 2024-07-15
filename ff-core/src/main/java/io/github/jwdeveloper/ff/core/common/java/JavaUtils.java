package io.github.jwdeveloper.ff.core.common.java;

import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class JavaUtils
{


    public static <T> void ifNotNull(T input, Consumer<T> action) {
        if(input == null)
            return;
        action.accept(input);
    }


    public static boolean isNull(Object o)
    {
        return  o == null;
    }

    public static boolean isNotNull(Object o)
    {
        return  o != null;
    }

    public static <T> T ifNull(T input, T value)
    {
        if(input != null)
        {
            return input;
        }
        return value;
    }

    public static void throwIfNull(Object input, String... message)
    {
       if(input != null)
       {
           return;
       }
       var builder = new MessageBuilder();
       for(var m : message)
       {
           builder.text(builder).space();
       }
       throw new RuntimeException(builder.toString());
    }

    public static boolean waitUntil(Supplier<Boolean> action)
    {
        while (!action.get())
        {
            try {
                Thread.sleep(1);
            }
            catch (Exception e)
            {
                return false;
            }
        }
        return true;
    }
}
