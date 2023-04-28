package io.github.jwdeveloper.ff.api.api.config.migrations;

import org.bukkit.configuration.file.YamlConfiguration;

public interface ConfigMigrator
{
     boolean isPluginUpdated(YamlConfiguration configuration);

     void makeMigration(YamlConfiguration configuration) throws InstantiationException, IllegalAccessException;
}
