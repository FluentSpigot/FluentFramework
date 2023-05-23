package io.github.jwdeveloper.ff.extension.updater;

import io.github.jwdeveloper.ff.extension.updater.api.UpdaterApiOptions;
import io.github.jwdeveloper.ff.extension.updater.implementation.FluentUpdaterExtension;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

import java.util.function.Consumer;

public class FluentUpdaterApi
{
    public static FluentApiExtension use(Consumer<UpdaterApiOptions> options)
    {
        return new FluentUpdaterExtension(options);
    }

    public static FluentApiExtension use()
    {
        return new FluentUpdaterExtension(e ->{});
    }
}
