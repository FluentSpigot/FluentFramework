package io.github.jwdeveloper.ff.plugin;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.spigot.commands.FluentCommand;
import io.github.jwdeveloper.ff.extension.translator.FluentTranslatorAPI;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPriority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FluentPluginBuilder {
    private final List<FluentApiExtension> apiExtensions;
    private final Plugin plugin;
    private String version;
    private ExtentionPriority priority;

    private Consumer<FluentApiSpigotBuilder> onConfiguration;

    private Consumer<FluentApiSpigot> onEnable;

    private Consumer<FluentApiSpigot> onDisable;

    public FluentPluginBuilder(Plugin plugin) {
        this.plugin = plugin;
        apiExtensions = new ArrayList<>();
        version = StringUtils.EMPTY;
        priority = ExtentionPriority.MEDIUM;
        onConfiguration = (e) -> {
        };
        onEnable = (e) -> {
        };
        onDisable = (e) -> {
        };
    }


    public FluentPluginBuilder withExtension(FluentApiExtension extension) {
        apiExtensions.add(extension);
        return this;
    }

    public FluentPluginBuilder withOnConfiguration(Consumer<FluentApiSpigotBuilder> event) {
        onConfiguration = event;
        return this;
    }

    public FluentPluginBuilder withOnFluentApiEnable(Consumer<FluentApiSpigot> event) {
        onEnable = event;
        return this;
    }

    public FluentPluginBuilder withOnFluentApiDisabled(Consumer<FluentApiSpigot> event) {
        onDisable = event;
        return this;
    }

    public FluentPluginBuilder withPriority(ExtentionPriority priority) {
        this.priority = priority;
        return this;
    }

    public FluentPluginBuilder withVersion(String version) {
        this.version = version;
        return this;
    }

    public FluentApiSpigot createOrDisablePlugin() {
        try {
            var api = create();
            if (api == null) {
                throw new Exception("Unable to create api instance");
            }
            return api;
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
        return null;
    }

    public FluentApiSpigot create() {
        var builder = FluentApiBuilder.create(plugin);
        builder.useExtension(FluentTranslatorAPI.useTranslator());
        for (var extension : apiExtensions) {
            builder.useExtension(extension);
        }

        builder.useExtension(new FluentApiExtension() {
            @Override
            public void onConfiguration(FluentApiSpigotBuilder builder) {
                onConfiguration.accept(builder);
            }

            @Override
            public String getVersion() {
                return version;
            }

            @Override
            public ExtentionPriority getPriority() {
                return priority;
            }

            @Override
            public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
                onEnable.accept(fluentAPI);
            }

            @Override
            public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {
                onDisable.accept(fluentAPI);
            }
        });


        var api = builder.build();
        if (api != null) {
            FluentCommand.create("disable")
                    .propertiesConfig(propertiesConfig ->
                    {
                        propertiesConfig.setDescription("Command only for plugin development purpose. Can be only trigger by Console. disables all plugins");
                        propertiesConfig.setUsageMessage("/disable");
                        propertiesConfig.setHideFromTabDisplay(true);
                    })
                    .eventsConfig(eventConfig ->
                    {
                        eventConfig.onConsoleExecute(consoleCommandEvent ->
                        {
                            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "Plugins disabled");
                            Bukkit.getPluginManager().disablePlugins();
                        });
                    })
                    .buildAndRegister();
        }
        return api;
    }
}
