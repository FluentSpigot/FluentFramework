package io.github.jwdeveloper.ff.core.files.yaml.implementation;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.yaml.api.YamlModelFactory;
import io.github.jwdeveloper.ff.core.files.yaml.api.YamlReader;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SimpleYamlReader implements YamlReader {
    private final YamlModelFactory factory;
    private final SimpleYamlModelMapper mapper;

    public SimpleYamlReader() {
        factory = new SimpleYamlModelFactory();
        mapper = new SimpleYamlModelMapper();
    }

    @Override
    public <T> YamlConfiguration toConfiguration(T data) throws Exception {
        var configuration = new YamlConfiguration();
        return toConfiguration(data, configuration);
    }

    @Override
    public <T> YamlConfiguration toConfiguration(T data, YamlConfiguration configuration) throws Exception{
        var model = factory.createModel(data.getClass());
        return mapper.mapToConfiguration(data, model, configuration, false);
    }

    @Override
    public <T> T fromConfiguration(File file, Class<T> clazz) throws Exception{

        var configuration = YamlConfiguration.loadConfiguration(file);
        return fromConfiguration(configuration, clazz);
    }

    @Override
    public <T> T fromConfiguration(YamlConfiguration configuration, Class<T> clazz) throws Exception {
        var instance = clazz.newInstance();
        return fromConfiguration(configuration, instance);
    }

    @Override
    public <T> T fromConfiguration(YamlConfiguration configuration, T object) throws Exception {
        return fromConfiguration(configuration, object, StringUtils.EMPTY);
    }
    @Override
    public <T> T fromConfiguration(YamlConfiguration configuration, T object, String defaultPath) throws Exception {
        var model = factory.createModel(object.getClass(),defaultPath);
        return (T)mapper.mapFromConfiguration(object,model, configuration);
    }
}
