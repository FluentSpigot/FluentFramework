package io.github.jwdeveloper.extensions.commands.api.annotations;

import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.AccessType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command
{
     String name() default  "";

     String description() default "";

     String shortDescription() default "";

     String label() default "";

     Class<?> parent() default Command.class;

     AccessType access() default AccessType.COMMAND_SENDER;
}
