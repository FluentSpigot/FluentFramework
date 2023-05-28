package io.github.jwdeveloper.ff.plugin.api.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public interface FluentConfig {
    Object getRequired(String name) ;

    <T> T getOrCreate(String path, T defaultValue, String... description);

    <T> T getOrCreate(ConfigProperty<T> configProperty);

    <T> T get(String name);

    <T> T toObject(Class<T> clazz);

    void save();

    void save(Object object);

    void save(Object object, String ymlPath);

    YamlConfiguration configFile();
}
