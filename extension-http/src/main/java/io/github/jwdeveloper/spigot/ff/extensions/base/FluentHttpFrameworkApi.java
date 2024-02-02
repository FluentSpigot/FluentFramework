package io.github.jwdeveloper.spigot.ff.extensions.base;

import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.spigot.ff.extensions.api.FluentHttpServerBuildAction;
import io.github.jwdeveloper.spigot.ff.extensions.api.FluentHttpServerSettings;

import java.util.function.Consumer;

public class FluentHttpFrameworkApi
{
    public static FluentApiExtension use(Consumer<FluentHttpServerSettings> builderConsumer)
    {
        return new HttpExtension();
    }
}
