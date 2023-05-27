package io.github.jwdeveloper.ff.plugin.api.config.migrations;

import org.bukkit.configuration.file.YamlConfiguration;

public interface ConfigMigrator
{

     boolean isConfigUpdated(YamlConfiguration configuration);

     void makeMigration(YamlConfiguration configuration) throws InstantiationException, IllegalAccessException;
}
