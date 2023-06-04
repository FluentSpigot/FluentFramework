package io.github.jwdeveloper.ff.tools;

import org.apiguardian.api.API;
import org.junit.jupiter.api.TestTemplate;
import org.junit.platform.commons.annotation.Testable;

import java.lang.annotation.*;

import static org.apiguardian.api.API.Status.STABLE;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@API(status = STABLE, since = "5.0")
@Testable
public @interface FluentTask {
}
