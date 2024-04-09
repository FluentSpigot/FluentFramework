package io.github.jwdeveloper.ff.extension.bai.craftings;

import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCraftingApi;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCraftingRegistry;
import io.github.jwdeveloper.ff.extension.bai.craftings.impl.SimpleCraftingApi;
import io.github.jwdeveloper.ff.extension.bai.craftings.impl.SimpleCraftingRegistry;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class FluentCraftingExtension implements FluentApiExtension {
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        var container = builder.container();
        container.registerSingleton(FluentCraftingRegistry.class, SimpleCraftingRegistry.class);
        container.registerSingleton(FluentCraftingApi.class, SimpleCraftingApi.class);
    }
}
