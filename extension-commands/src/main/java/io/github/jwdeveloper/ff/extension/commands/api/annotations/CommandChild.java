package io.github.jwdeveloper.ff.extension.commands.api.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Repeatable(CommandChildGroup.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandChild
{
    Class<?> childClass();
}
