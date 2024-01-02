package io.github.jwdeveloper.ff.extension.commands.api.annotations;

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
     String[] permissions() default "";
     String usage() default "";
     boolean debug() default false;
     AccessType access() default AccessType.COMMAND_SENDER;
     boolean hideFromDisplay() default false;
}
