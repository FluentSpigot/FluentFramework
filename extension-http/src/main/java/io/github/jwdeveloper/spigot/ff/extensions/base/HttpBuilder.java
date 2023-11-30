package io.github.jwdeveloper.spigot.ff.extensions.base;

import io.javalin.config.JavalinConfig;

import java.util.function.Consumer;

public class HttpBuilder
{
    public HttpBuilder configure(Consumer<JavalinConfig> consumer)
    {
        return this;
    }

    public HttpBuilder use()
    {
        return this;
    }

    public HttpBuilder usePlayerAutentication()
    {

        return this;
    }
}
