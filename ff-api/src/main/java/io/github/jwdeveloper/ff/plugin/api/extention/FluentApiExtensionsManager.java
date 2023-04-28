package io.github.jwdeveloper.ff.plugin.api.extention;


import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

public interface FluentApiExtensionsManager {
    void register(FluentApiExtension extension);

    void register(FluentApiExtension extension, ExtentionPiority priority);

    void registerLow(FluentApiExtension extension);

    void onEnable(FluentApiSpigot fluentAPI);

    void onDisable(FluentApiSpigot fluentAPI);

    void onConfiguration(FluentApiSpigotBuilder builder);
}
