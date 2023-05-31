package io.github.jwdeveloper.ff.plugin.implementation.config;

import io.github.jwdeveloper.ff.core.common.logger.PluginLogger;
import io.github.jwdeveloper.ff.core.files.yaml.implementation.SimpleYamlReader;
import io.github.jwdeveloper.ff.core.injector.api.events.events.OnInjectionEvent;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.assembly_scanner.JarScanner;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.plugin.implementation.config.migrations.DefaultConfigMigrator;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.ConfigOptions;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.ConfigOptionsImpl;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public class FluentConfigManager {
    private final JarScanner jarScanner;
    private final PluginLogger logger;
    private final FluentConfig fluentConfig;
    private final Map<Class<?>, String> bindings;

    public FluentConfigManager(JarScanner jarScanner,
                               PluginLogger logger,
                               FluentConfig fluentConfig)
                            {
        bindings = new HashMap<>();
        this.fluentConfig = fluentConfig;
        this.jarScanner = jarScanner;
        this.logger = logger;
    }


    public FluentConfig getConfig()
    {
        return fluentConfig;
    }

    public void bindToConfig(Class<?> clazz, String ymlPath) {
        bindings.put(clazz, ymlPath);
    }

    public void handleRegisterBindings(FluentApiSpigotBuilder extension) {
        for (var bindingClazz : bindings.keySet()) {
            extension.container().registerSigleton(bindingClazz);
        }
    }

    public void onMigration(FluentApiExtension extension) {
        new DefaultConfigMigrator(extension, jarScanner, logger).makeMigration(fluentConfig.configFile());
    }

    public void handleClassMappingFromFile(FluentApiSpigot extension) {
        var ymlReader = new SimpleYamlReader();
        try {
            for (var entry : bindings.entrySet()) {
                var instance = extension.container().findInjection(entry.getKey());
                ymlReader.fromConfiguration(fluentConfig.configFile(), instance,entry.getValue());
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to map config", e);
        }

    }


    public void onSaveConfig(FluentApiSpigot extension) {
        for (var entry : bindings.entrySet()) {
            try {
                var instance = extension.container().findInjection(entry.getKey());
                fluentConfig.save(instance, entry.getValue());
            } catch (Exception e) {
                throw new RuntimeException("Unable to map config", e);
            }
        }
    }

    public Object onConfigOptionsInjectionCall(OnInjectionEvent event)
    {
        if(!event.input().isAssignableFrom(ConfigOptions.class))
        {
            return event.output();
        }

        if(!event.hasGenericParameters())
        {
            throw new RuntimeException("ConfigOptions need to has specified config type");
        }

        try
        {
            var parameterType = event.inputGenericParameters()[0];
            Class<?> parameterClass = null;
            if(parameterType instanceof ParameterizedType pt)
            {
                 parameterClass = Class.forName(pt.getActualTypeArguments()[0].getTypeName());
            }
            else
            {
                parameterClass = Class.forName(parameterType.getTypeName());
            }


            if(!bindings.containsKey(parameterClass))
            {
                throw new RuntimeException("ConfigOptions can't be created since class  "+parameterClass.getSimpleName()+" is not register to bindings");
            }
            var instance = event.container().find(parameterClass);
            var ymlPath = bindings.get(parameterClass);
            return new ConfigOptionsImpl<>(fluentConfig, instance, ymlPath);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to create ConfigOptions for "+event.input().getSimpleName());
        }

    }
}
