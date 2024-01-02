package io.github.jwdeveloper.ff.plugin.implementation.config;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.files.yaml.implementation.SimpleYamlModelFactory;
import io.github.jwdeveloper.ff.plugin.api.config.ConfigProperty;
import io.github.jwdeveloper.ff.core.common.TextBuilder;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.files.yaml.implementation.SimpleYamlModelMapper;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@Getter
public class FluentConfigImpl implements FluentConfig {

    private YamlConfiguration fileConfiguration;
    private final String path;


    public FluentConfigImpl(YamlConfiguration fileConfiguration, String path) {
        this.fileConfiguration = fileConfiguration;
        this.path = path;
    }

    public <T> T get(String name) {
        return (T) fileConfiguration.get(name);
    }

    public YamlConfiguration configFile() {
        return fileConfiguration;
    }

    @Override
    public <T> T toObject(Class<T> clazz) {
        return null;
    }

    @Override
    public void save() {
        try {
            fileConfiguration.save(path);
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Unable to save config path!", e);
        }

    }


    public void save(Object configSection) {
        save(configSection, StringUtils.EMPTY);
    }

    @Override
    public void save(Object configSection, String ymlPath) {
        try {
            var factory = new SimpleYamlModelFactory();
            var model = factory.createModel(configSection.getClass(), ymlPath);
            var mapper = new SimpleYamlModelMapper();
            mapper.mapToConfiguration(configSection, model, fileConfiguration, true);
            fileConfiguration.save(path);
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Unable to save config path!", e);
        }
    }

    @Override
    public YamlConfiguration loadSnapshot() {
        var file = new File(path);
        return YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void setConfig(YamlConfiguration config) {
        fileConfiguration = config;
    }


    public Object getRequired(String name) {
        var value = get(name);
        if (value == null) {
            throw new RuntimeException("Value " + name + " is required");
        }
        return value;
    }

    @Override
    public <T> T getOrCreate(String path, T defaultValue, String... description) {

        if (!fileConfiguration.contains(path)) {
            fileConfiguration.set(path, defaultValue);
        }

        if (description.length != 0) {
            var builder = new TextBuilder();
            builder.text(fileConfiguration.options().header());
            builder.newLine();
            builder.text(path);
            builder.newLine();
            for (var desc : description) {
                builder.bar(" ", 3).text(desc).newLine();
            }
            builder.newLine();
            fileConfiguration.options().header(builder.toString());
        }
        save();
        return (T) fileConfiguration.get(path);
    }

    public void set(String path, Object value) {
        fileConfiguration.set(path, value);
    }

    @Override
    public <T> T getOrCreate(ConfigProperty<T> configProperty) {
        return getOrCreate(configProperty.path(), configProperty.defaultValue(), configProperty.description());
    }
}
