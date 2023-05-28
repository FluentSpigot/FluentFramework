package io.github.jwdeveloper.ff.plugin.implementation.config.options;

import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;

public class ConfigOptionsImpl<T> implements ConfigOptions<T> {
    private final FluentConfig fluentConfig;
    private final Object target;
    private final String path;

    public ConfigOptionsImpl(FluentConfig fluentConfig, Object target, String path)
    {
        this.fluentConfig = fluentConfig;
        this.target = target;
        this.path = path;
    }

    @Override
    public void save() {
       fluentConfig.save(target, path);
    }

    @Override
    public T get() {
        return (T)target;
    }

}
