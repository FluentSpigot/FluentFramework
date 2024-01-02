package io.github.jwdeveloper.ff.plugin.api.config;

import org.bukkit.configuration.file.YamlConfiguration;

public interface FluentConfig {
    Object getRequired(String name);

    <T> T getOrCreate(String path, T defaultValue, String... description);

    <T> T getOrCreate(ConfigProperty<T> configProperty);

    <T> T get(String name);

    <T> T toObject(Class<T> clazz);

    void set(String path, Object value);

    void save();

    void save(Object object);

    void save(Object object, String ymlPath);

    /**
     *
     * @return loads current state of config from disk
     */
    YamlConfiguration loadSnapshot();

    /**
     *  sets handle for config file
     * @param config
     */
    void setConfig(YamlConfiguration config);


    /**
     *
     * @return virtual YamlConfiguration that is saved and handle by methods
     */
    YamlConfiguration configFile();
}
