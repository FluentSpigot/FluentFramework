package io.github.jwdeveloper.ff.extension.files;

import io.github.jwdeveloper.ff.extension.files.implementation.FluentFilesExtension;
import io.github.jwdeveloper.ff.extension.files.implementation.config.FluentFilesOptions;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

import java.util.function.Consumer;

public class FluentFilesApi
{
    public static FluentApiExtension use(Consumer<FluentFilesOptions> options)
    {
         return new FluentFilesExtension(options);
    }
}
