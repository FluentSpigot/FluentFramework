package io.github.jwdeveloper.ff.core.files.yaml.api.annotations;

import lombok.Data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE} )
public @interface YamlSection {

    String name() default "";

    String path() default "";

    String description() default "";

    boolean required() default false;
}
