package io.github.jwdeveloper.ff.extension.bai.items;

import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemApi;
import io.github.jwdeveloper.ff.extension.bai.items.api.FluentItemRegistry;
import io.github.jwdeveloper.ff.extension.bai.craftings.api.FluentCraftingRegistry;
import io.github.jwdeveloper.ff.extension.bai.items.api.mappers.FluentItemStackMapper;
import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemScheamFactory;
import io.github.jwdeveloper.ff.extension.bai.items.impl.SimpleItemApi;
import io.github.jwdeveloper.ff.extension.bai.items.impl.SimpleItemRegistry;
import io.github.jwdeveloper.ff.extension.bai.craftings.impl.SimpleCraftingRegistry;
import io.github.jwdeveloper.ff.extension.bai.items.impl.mappers.ItemStackMapper;
import io.github.jwdeveloper.ff.extension.bai.items.impl.schema.SchemaFactory;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

public class FluentItemExtension implements FluentApiExtension {


    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        var container = builder.container();

        container.registerSingleton(FluentItemScheamFactory.class, SchemaFactory.class);
        container.registerSingleton(FluentItemApi.class, SimpleItemApi.class);
        container.registerSingleton(FluentItemRegistry.class, SimpleItemRegistry.class);
        container.registerSingleton(FluentItemStackMapper.class, ItemStackMapper.class);
    }




}