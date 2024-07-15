package io.github.jwdeveloper.ff.advancements;

import io.github.jwdeveloper.ff.advancements.implementation.AdvancementExtension;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;


public class FluentAdvancementApi
{
    public static FluentApiExtension use()
    {
        return new AdvancementExtension();
    }
}
