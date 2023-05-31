package io.github.jwdeveloper.ff.plugin.implementation;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;

public class FluentApiMeta
{
    private final FluentConfig config;
    private final String path = "plugin-meta.environment";

    public FluentApiMeta(FluentConfig config)
    {
        this.config = config;
    }

    public String getEnvironment()
    {
        return config.getOrCreate(path,"debbug");
    }

    public void setEnvironment(String environment)
    {
        config.set(path, environment);
        config.save();
    }

    public boolean isEnvironment(String environment)
    {
        if(StringUtils.isNullOrEmpty(environment))
        {
            throw new RuntimeException("Environment should not be null");
        }

        var env = getEnvironment();
        if(StringUtils.isNullOrEmpty(env))
        {
            return false;
        }
        return environment.equalsIgnoreCase(env);
    }

    public boolean isDebbug()
    {
         return isEnvironment("debbug");
    }

    public boolean isRelease()
    {
        return isEnvironment("release");
    }
}
