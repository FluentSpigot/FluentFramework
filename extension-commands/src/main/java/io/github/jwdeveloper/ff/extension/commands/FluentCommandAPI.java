package io.github.jwdeveloper.ff.extension.commands;

import io.github.jwdeveloper.ff.extension.commands.api.FluentCommandOptions;
import io.github.jwdeveloper.ff.extension.commands.implementation.FluentCommandExtension;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

import java.util.function.Consumer;

public class FluentCommandAPI
{
    public static FluentApiExtension use(Consumer<FluentCommandOptions> options)
    {
        return new FluentCommandExtension(options);
    }
}
