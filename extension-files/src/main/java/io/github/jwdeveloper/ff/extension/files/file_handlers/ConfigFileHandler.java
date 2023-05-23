package io.github.jwdeveloper.ff.extension.files.file_handlers;

import io.github.jwdeveloper.ff.core.files.yaml.api.YamlReader;
import io.github.jwdeveloper.ff.core.files.yaml.implementation.SimpleYamlReader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ConfigFileHandler implements FileHandler {

    private final List<Object> configSections = new ArrayList<>();
    private final YamlReader reader;
    public ConfigFileHandler()
    {
        reader = new SimpleYamlReader();
    }

    public void load() throws IllegalAccessException, InstantiationException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException {
        //TODO this
       /* var pluginConfig =  (YamlConfiguration) FluentApi.plugin().getConfig();
        for (var configSection: configSections)
        {
            var result = reader.fromConfiguration(pluginConfig,configSection.getClass());
            ObjectUtility.copyToObject(result, configSection, result.getClass());
        }
        pluginConfig.save(FluentApi.path()+ File.separator+"config.yml");*/
    }


    @Override
    public void save()
    {

    }

    @Override
    public void addObject(Object object) {
        configSections.add(object);
    }
    @Override
    public void removeObject(Object object) {
        configSections.remove(object);
    }
}
