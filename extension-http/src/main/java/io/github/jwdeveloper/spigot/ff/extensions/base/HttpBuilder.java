package io.github.jwdeveloper.spigot.ff.extensions.base;

import io.javalin.config.JavalinConfig;

import java.util.function.Consumer;

public class HttpBuilder {
    public HttpBuilder configure(Consumer<JavalinConfig> consumer) {
        return this;
    }

    public HttpBuilder use() {
        return this;
    }

    public HttpBuilder usePlayerAutentication() {
        FluentHttpFrameworkApi.use(builder ->
        {
            builder.setPort(1233);
            builder.setIp("dupa");
            builder.setUseSll(true);
            builder.setTokenSecret("123");
            builder.setTokenTimeout(123);
            builder.setOnBuilding((fluentApiSpigotBuilder, httpServerBuilder) ->
            {

            });
        });
        return this;
    }
}
