package io.github.jwdeveloper.ff.core.files.yaml.api;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public interface YamlReader {
    <T> YamlConfiguration toConfiguration(T data) throws Exception;

    <T> YamlConfiguration toConfiguration(T data, YamlConfiguration configuration) throws Exception;
    <T> T fromConfiguration(File file, Class<T> clazz) throws Exception;

    <T> T fromConfiguration(YamlConfiguration configuration, Class<T> clazz) throws Exception;
     <T> T fromConfiguration(YamlConfiguration configuration, T object) throws Exception;
     <T> T fromConfiguration(YamlConfiguration configuration, T object, String defaultPath) throws Exception;
}
