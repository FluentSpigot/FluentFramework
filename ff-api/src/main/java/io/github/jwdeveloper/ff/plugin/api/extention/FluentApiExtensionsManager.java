package io.github.jwdeveloper.ff.plugin.api.extention;


import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;

public interface FluentApiExtensionsManager {
    void register(FluentApiExtension extension);

    void register(FluentApiExtension extension, ExtentionPriority priority);

    void registerLow(FluentApiExtension extension);

    void onEnable(FluentApiSpigot fluentAPI);

    void onDisable(FluentApiSpigot fluentAPI);

    void onConfiguration(FluentApiSpigotBuilder builder);
}
