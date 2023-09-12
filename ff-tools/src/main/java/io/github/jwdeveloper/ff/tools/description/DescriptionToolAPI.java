package io.github.jwdeveloper.ff.tools.description;

import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.tools.description.options.DescriptionOptions;

import java.util.function.Consumer;

public class DescriptionToolAPI
{
    public static FluentApiExtension use(Consumer<DescriptionOptions> options)
    {
            var descriptionOptions = new DescriptionOptions();
            options.accept(descriptionOptions);
            return new DescriptionExtension(descriptionOptions);

    }
}
