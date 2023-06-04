package io.github.jwdeveloper.extensions.commands.api.annotations;


import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.ArgumentType;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Repeatable(ArgumentGroup.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Argument {

    String name();

    String description() default "";

    ArgumentType argumentType() default ArgumentType.TEXT;
}