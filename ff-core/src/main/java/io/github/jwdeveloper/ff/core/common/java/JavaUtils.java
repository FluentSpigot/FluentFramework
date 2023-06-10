package io.github.jwdeveloper.ff.core.common.java;

import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;

import java.util.function.Consumer;

public class JavaUtils
{


    public static <T> void ifNotNull(T input, Consumer<T> action) {
        if(input == null)
            return;
        action.accept(input);
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
}
