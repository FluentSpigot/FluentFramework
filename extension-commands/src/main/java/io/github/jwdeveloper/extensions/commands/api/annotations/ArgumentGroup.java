package io.github.jwdeveloper.extensions.commands.api.annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)

public @interface ArgumentGroup {
    Argument[] value();
}