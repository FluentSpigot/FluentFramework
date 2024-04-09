package io.github.jwdeveloper.ff.extension.bai;

import io.github.jwdeveloper.ff.extension.bai.blocks.FluentBlockExtension;
import io.github.jwdeveloper.ff.extension.bai.blocks.impl.DisplayFactory;
import io.github.jwdeveloper.ff.extension.bai.common.listeners.BlockListener;
import io.github.jwdeveloper.ff.extension.bai.common.listeners.CraftingListener;
import io.github.jwdeveloper.ff.extension.bai.common.listeners.ItemListener;
import io.github.jwdeveloper.ff.extension.bai.common.listeners.ItemUseListener;
import io.github.jwdeveloper.ff.extension.bai.craftings.FluentCraftingExtension;
import io.github.jwdeveloper.ff.extension.bai.items.FluentItemExtension;
import io.github.jwdeveloper.ff.extension.bai.common.FrameworkSettings;
import io.github.jwdeveloper.ff.extension.bai.items.api.schema.FluentItemSchema;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.Plugin;

import java.util.UUID;
import java.util.function.Consumer;

public class BlocksAndItemsExtension implements FluentApiExtension {

    private final Consumer<FrameworkSettings> settingsConsumer;

    public BlocksAndItemsExtension(Consumer<FrameworkSettings> settingsConsumer) {
        this.settingsConsumer = settingsConsumer;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        var container = builder.container();

        container.registerSingleton(BlockAndItemsApi.class);
        container.registerSingleton(DisplayFactory.class);
        container.registerSingleton(FrameworkSettings.class, container1 ->
        {
            var settings = getDefaultSettings(builder.plugin());
            settingsConsumer.accept(settings);
            return settings;
        });

        //Listeners
        container.registerSingleton(CraftingListener.class);
        container.registerSingleton(ItemListener.class);
        container.registerSingleton(ItemUseListener.class);
        container.registerSingleton(BlockListener.class);

        builder.useExtension(new FluentItemExtension());
        builder.useExtension(new FluentBlockExtension());
        builder.useExtension(new FluentCraftingExtension());
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

        fluentAPI.container().findInjection(CraftingListener.class);
        fluentAPI.container().findInjection(ItemListener.class);
        fluentAPI.container().findInjection(ItemUseListener.class);
        fluentAPI.container().findInjection(BlockListener.class);
    }

    private FrameworkSettings getDefaultSettings(Plugin plugin) {
        var settings = new FrameworkSettings();
        settings.setPluginSessionId(UUID.randomUUID());

        settings.setPluginSessionKey(new NamespacedKey(plugin, "fluent.bai.session"));
        settings.setPluginVersionKey(new NamespacedKey(plugin, "fluent.bai.version"));

        settings.setNameKey(new NamespacedKey(plugin, "fluent.bai.name"));
        settings.setIdKey(new NamespacedKey(plugin, "fluent.bai.id"));
        settings.setStorageKey(new NamespacedKey(plugin, "fluent.bai.storage"));
        settings.setTagKey(new NamespacedKey(plugin, "fluent.bai.tag"));
        settings.setBlockMaterial(Material.BARRIER);
        settings.setDefaultSchema(new FluentItemSchema());
        return settings;
    }
}
