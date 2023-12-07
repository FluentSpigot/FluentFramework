package io.github.jwdeveloper.ff.extension.commands.api.annotations;


import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.ArgumentDisplay;
import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.ArgumentType;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Repeatable(ArgumentGroup.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Argument {
    String name();
    String description() default "";
    ArgumentDisplay displayMode() default ArgumentDisplay.NAME;
    ArgumentType argumentType() default ArgumentType.TEXT;
    String onTabComplete() default "";
    String onValidation() default "";
}