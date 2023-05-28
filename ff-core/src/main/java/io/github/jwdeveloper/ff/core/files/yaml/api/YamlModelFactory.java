package io.github.jwdeveloper.ff.core.files.yaml.api;

import io.github.jwdeveloper.ff.core.files.yaml.api.models.YamlContent;
import io.github.jwdeveloper.ff.core.files.yaml.api.models.YamlModel;

public interface YamlModelFactory
{
     <T> YamlContent createModel(Class<T> clazz) throws ClassNotFoundException;
    <T> YamlContent createModel(Class<T> clazz, String ymlPath) throws ClassNotFoundException;
}
