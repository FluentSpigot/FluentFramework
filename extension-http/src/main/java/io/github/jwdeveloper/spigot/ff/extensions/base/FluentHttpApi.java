package io.github.jwdeveloper.spigot.ff.extensions.base;

import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

import java.util.function.Consumer;

public class FluentHttpApi
{
    public static FluentApiExtension use(Consumer<HttpBuilder> builderConsumer)
    {
        return new HttpExtension();
    }
}
