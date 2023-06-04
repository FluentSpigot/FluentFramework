package io.github.jwdeveloper.ff.extension.commands.api.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandChildGroup
{
    CommandChild[] value();
}
