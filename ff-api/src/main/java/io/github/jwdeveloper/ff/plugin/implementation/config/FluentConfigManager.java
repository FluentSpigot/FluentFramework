package io.github.jwdeveloper.ff.plugin.implementation.config;

import io.github.jwdeveloper.ff.core.common.logger.BukkitLogger;
import io.github.jwdeveloper.ff.core.files.yaml.implementation.SimpleYamlReader;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.assembly_scanner.JarScanner;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.plugin.implementation.config.migrations.DefaultConfigMigrator;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class FluentConfigManager {
    private final JarScanner jarScanner;
    private final BukkitLogger logger;
    private final Plugin plugin;
    private final Map<Class<?>, String> bindings;
    private FluentConfig config;

    public FluentConfigManager(JarScanner jarScanner,
                               BukkitLogger logger,
                               Plugin plugin) {
        bindings = new HashMap<>();
        this.jarScanner = jarScanner;
        this.logger = logger;
        this.plugin = plugin;
    }


    public FluentConfig getConfig() {

        if (config != null) {
            return config;
        }

        try {
            config = new FluentConfigLoader(plugin).load();
            return config;
        } catch (Exception e) {
            throw new RuntimeException("Unable to load config file", e);
        }

    }

    public void bindToConfig(Class<?> clazz, String ymlPath) {
        bindings.put(clazz, ymlPath);
    }

    public void handleRegisterBindings(FluentApiSpigotBuilder extension) {
        for (var bindingClazz : bindings.keySet()) {
            extension.container().registerSigleton(bindingClazz);
        }
    }

    public void handleMigration(FluentApiExtension extension) {
        new DefaultConfigMigrator(extension, jarScanner, logger).makeMigration(getConfig().configFile());
    }

    public void handleOptionsClassMapping(FluentApiSpigot extension) {
        var ymlReader = new SimpleYamlReader();
        try {
            for (var entry : bindings.entrySet()) {
                var instance = extension.container().findInjection(entry.getKey());
                ymlReader.fromConfiguration(getConfig().configFile(), instance,entry.getValue());
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to map config", e);
        }

    }


    public void handleSaveConfig(FluentApiSpigot extension) {
        for (var entry : bindings.entrySet()) {
            try {
                var instance = extension.container().findInjection(entry.getKey());
                config.save(instance, entry.getValue());
            } catch (Exception e) {
                throw new RuntimeException("Unable to map config", e);
            }
        }
    }
}
