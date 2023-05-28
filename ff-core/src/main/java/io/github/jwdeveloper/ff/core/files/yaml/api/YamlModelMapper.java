package io.github.jwdeveloper.ff.core.files.yaml.api;

import io.github.jwdeveloper.ff.core.files.yaml.api.models.YamlContent;
import io.github.jwdeveloper.ff.core.files.yaml.api.models.YamlModel;
import org.bukkit.configuration.file.YamlConfiguration;

import java.lang.reflect.InvocationTargetException;

public interface YamlModelMapper {
    public <T> YamlConfiguration mapToConfiguration(T data, YamlContent model, YamlConfiguration configuration, boolean overrite) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;
    public Object mapFromConfiguration(Object object, YamlContent model, YamlConfiguration configuration) throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, Exception;
}
