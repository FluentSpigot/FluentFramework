package io.github.jwdeveloper.ff.extension.resourcepack;
import io.github.jwdeveloper.ff.extension.resourcepack.implementation.ResourcepackExtention;
import io.github.jwdeveloper.ff.extension.resourcepack.api.ResourcepackOptions;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

import java.util.function.Consumer;

public class FluentResourcepackApi
{
    public static FluentApiExtension use(Consumer<ResourcepackOptions> consumer)
    {
        return new ResourcepackExtention(consumer);
    }


}
