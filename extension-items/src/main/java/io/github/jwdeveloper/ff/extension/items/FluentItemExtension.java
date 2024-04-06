package io.github.jwdeveloper.ff.extension.items;

import io.github.jwdeveloper.ff.extension.items.api.FluentItemApi;
import io.github.jwdeveloper.ff.extension.items.api.FluentItemRegistry;
import io.github.jwdeveloper.ff.extension.items.api.config.FluentItemApiSettings;
import io.github.jwdeveloper.ff.extension.items.api.crafting.FluentCraftingRegistry;
import io.github.jwdeveloper.ff.extension.items.api.mappers.FluentItemStackMapper;
import io.github.jwdeveloper.ff.extension.items.api.schema.FluentItemScheamFactory;
import io.github.jwdeveloper.ff.extension.items.api.schema.FluentItemSchema;
import io.github.jwdeveloper.ff.extension.items.impl.SimpleItemApi;
import io.github.jwdeveloper.ff.extension.items.impl.SimpleItemRegistry;
import io.github.jwdeveloper.ff.extension.items.impl.crafting.SimpleCraftingRegistry;
import io.github.jwdeveloper.ff.extension.items.impl.mappers.ItemStackMapper;
import io.github.jwdeveloper.ff.extension.items.impl.schema.SchemaFactory;
import io.github.jwdeveloper.ff.extension.items.spigot.listeners.CraftingListener;
import io.github.jwdeveloper.ff.extension.items.spigot.listeners.ItemListener;
import io.github.jwdeveloper.ff.extension.items.spigot.listeners.ItemUseListener;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.UUID;
import java.util.function.Consumer;

public class FluentItemExtension implements FluentApiExtension {

    private final Consumer<FluentItemApiSettings> consumer;

    public FluentItemExtension(Consumer<FluentItemApiSettings> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        var container = builder.container();
        container.registerSingleton(FluentItemApiSettings.class, container1 ->
        {
            var settings = getDefaultSettings(builder.plugin());
            consumer.accept(settings);
            return settings;
        });
        container.registerSingleton(FluentItemScheamFactory.class, SchemaFactory.class);
        container.registerSingleton(FluentItemApi.class, SimpleItemApi.class);
        container.registerSingleton(FluentItemRegistry.class, SimpleItemRegistry.class);
        container.registerSingleton(FluentItemStackMapper.class, ItemStackMapper.class);
        container.registerSingleton(FluentCraftingRegistry.class, SimpleCraftingRegistry.class);


        container.registerSingleton(CraftingListener.class);
        container.registerSingleton(ItemListener.class);
        container.registerSingleton(ItemUseListener.class);
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

        fluentAPI.container().findInjection(CraftingListener.class);
        fluentAPI.container().findInjection(ItemListener.class);
        fluentAPI.container().findInjection(ItemUseListener.class);
    }

    private FluentItemApiSettings getDefaultSettings(Plugin plugin) {
        var settings = new FluentItemApiSettings();
        settings.setPluginSessionId(UUID.randomUUID());

        settings.setPluginSessionKey(new NamespacedKey(plugin, "fluent.items.session"));
        settings.setPluginVersionKey(new NamespacedKey(plugin, "fluent.items.version"));

        settings.setItemNameKey(new NamespacedKey(plugin, "fluent.items.name"));
        settings.setItemIdKey(new NamespacedKey(plugin, "fluent.items.id"));
        settings.setItemStorageKey(new NamespacedKey(plugin, "fluent.items.storage"));
        settings.setItemTagKey(new NamespacedKey(plugin, "fluent.items.tag"));

        settings.setDefaultSchema(new FluentItemSchema());
        return settings;
    }
}