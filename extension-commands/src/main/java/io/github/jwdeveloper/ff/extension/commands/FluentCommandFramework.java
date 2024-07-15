package io.github.jwdeveloper.ff.extension.commands;

import io.github.jwdeveloper.ff.extension.commands.api.CommandApi;
import io.github.jwdeveloper.ff.extension.commands.api.data.FluentCommandOptions;
import io.github.jwdeveloper.ff.extension.commands.implementation.CommandExtension;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

import java.util.function.Consumer;

public class FluentCommandFramework {
    public static Class<CommandApi> API = CommandApi.class;

    public static FluentApiExtension use(Consumer<FluentCommandOptions> options) {
        return new CommandExtension(options);
    }

    public static FluentApiExtension use() {
        return new CommandExtension(fluentCommandOptions ->
        {
        });
    }
}
