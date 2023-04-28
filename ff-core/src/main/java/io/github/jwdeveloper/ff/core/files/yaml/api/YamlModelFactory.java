package io.github.jwdeveloper.ff.core.files.yaml.api;

import io.github.jwdeveloper.ff.core.files.yaml.api.models.YamlModel;

public interface YamlModelFactory
{
    public <T> YamlModel createModel(Class<T> clazz) throws ClassNotFoundException;
}
