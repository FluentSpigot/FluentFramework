package io.github.jwdeveloper.ff.plugin.api.config.migrations;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public interface ConfigMigration
{

    String version();
    void onUpdate(YamlConfiguration config) throws IOException;

    default void onDowngrade(YamlConfiguration config) throws IOException
    {
    }
}
