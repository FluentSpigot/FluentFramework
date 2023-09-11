package io.github.jwdeveloper.ff.tools;

import org.junit.jupiter.api.Test;

import java.lang.annotation.*;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Test
public @interface FluentFrameworkTask {
}
