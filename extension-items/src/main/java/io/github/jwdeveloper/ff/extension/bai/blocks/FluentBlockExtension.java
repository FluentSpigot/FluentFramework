package io.github.jwdeveloper.ff.extension.bai.blocks;

import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockApi;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.FluentBlockRegistry;
import io.github.jwdeveloper.ff.extension.bai.blocks.api.builder.BlockBuilder;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.SimpleBlockApi;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.DisplayFactory;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.SimpleBlockRegistry;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.builder.BlockBehaviourBuilder;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class FluentBlockExtension implements FluentApiExtension {
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        var container = builder.container();
        container.registerSingleton(FluentBlockApi.class, SimpleBlockApi.class);
        container.registerSingleton(FluentBlockRegistry.class, SimpleBlockRegistry.class);
        container.registerTransient(BlockBuilder.class, BlockBehaviourBuilder.class);
        container.registerSingleton(DisplayFactory.class);
    }
}
