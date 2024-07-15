package io.github.jwdeveloper.ff.models.impl;

import io.github.jwdeveloper.ff.models.api.DisplayModelApi;
import io.github.jwdeveloper.ff.models.api.FluentDisplayModelRegistry;
import io.github.jwdeveloper.ff.models.impl.parsers.BlockBenchModelParser;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class ModelExtension implements FluentApiExtension {

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        var container = builder.container();
        container.registerSingleton(BlockBenchModelParser.class);
        container.registerTransient(DisplayModelApi.class, DisplayModelApiImpl.class);
        container.registerSingleton(FluentDisplayModelRegistry.class, DisplayModelRegistry.class);
    }
}
