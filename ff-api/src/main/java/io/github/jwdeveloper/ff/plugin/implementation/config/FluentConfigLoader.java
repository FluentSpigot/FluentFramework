package io.github.jwdeveloper.ff.plugin.implementation.config;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.files.yaml.api.YamlReader;
import io.github.jwdeveloper.ff.core.files.yaml.implementation.SimpleYamlReader;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class FluentConfigLoader {
    private final Plugin plugin;
    public FluentConfigLoader(Plugin plugin) {
        this.plugin = plugin;
    }

    public FluentConfigImpl load() throws Exception
    {
        var path = FileUtility.pluginPath(plugin) + File.separator + "config.yml";
        var yamlConfiguration = getConfigFile(path);
        yamlConfiguration.options().header(StringUtils.EMPTY);
        yamlConfiguration.save(path);
        return new FluentConfigImpl(yamlConfiguration, path);
    }

    private YamlConfiguration getConfigFile(String path) throws IOException {
        if (!FileUtility.pathExists(path)) {
            var yamlConfig = new YamlConfiguration();
            yamlConfig.save(path);
            return yamlConfig;
        }
        var file = new File(path);
        return YamlConfiguration.loadConfiguration(file);
    }

}
