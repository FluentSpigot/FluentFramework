package io.github.jwdeveloper.ff.extension.mysql.api.annotations;




import io.github.jwdeveloper.ff.extension.mysql.api.DbTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Column
{
    String name() default "";

    String type() default DbTypes.VARCHAR;

    int size() default 15;


}
