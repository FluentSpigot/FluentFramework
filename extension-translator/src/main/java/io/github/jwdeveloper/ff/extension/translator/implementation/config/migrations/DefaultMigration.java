package io.github.jwdeveloper.ff.extension.translator.implementation.config.migrations;

import io.github.jwdeveloper.ff.plugin.api.config.migrations.ExtensionMigration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class DefaultMigration implements ExtensionMigration
{
    @Override
    public String version() {
        return "1.0.0";
    }

    @Override
    public void onUpdate(YamlConfiguration config) throws IOException {

    }
}
