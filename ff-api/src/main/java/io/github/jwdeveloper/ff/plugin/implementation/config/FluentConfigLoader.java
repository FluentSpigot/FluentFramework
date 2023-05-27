package io.github.jwdeveloper.ff.plugin.implementation.config;

import io.github.jwdeveloper.ff.plugin.api.assembly_scanner.JarScanner;
import io.github.jwdeveloper.ff.plugin.implementation.config.migrations.FluentConfigMigrator;
import io.github.jwdeveloper.ff.plugin.implementation.config.sections.DefaultConfigSection;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.files.yaml.api.YamlReader;
import io.github.jwdeveloper.ff.core.files.yaml.implementation.SimpleYamlReader;
import io.github.jwdeveloper.ff.plugin.api.config.ConfigSection;
import io.github.jwdeveloper.ff.plugin.implementation.assemby_scanner.JarScannerImpl;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FluentConfigLoader {
    private final YamlReader yamlMapper;
    private final JarScanner assemblyScanner;
    private final Plugin plugin;
    public FluentConfigLoader(Plugin plugin, JarScanner assemblyScanner) {
        yamlMapper = new SimpleYamlReader();
        this.assemblyScanner = assemblyScanner;
        this.plugin = plugin;
    }



    public FluentConfigImpl load() throws Exception {
        var path = FileUtility.pluginPath(plugin) + File.separator + "config.yml";
        var yamlConfiguration = getConfigFile(path);
        yamlConfiguration.options().header(StringUtils.EMPTY);
        var sections = createAndMapSections(yamlConfiguration);
        yamlConfiguration.save(path);
        return new FluentConfigImpl(yamlConfiguration, path, false, false);
    }


    private List<ConfigSection> createAndMapSections(YamlConfiguration configuration) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException {

        var sections = new ArrayList<ConfigSection>();
        var sectionsClasses = assemblyScanner.findByInterface(ConfigSection.class);
        for (var clazz : sectionsClasses) {
            var sectionObject = (ConfigSection) clazz.newInstance();
            yamlMapper.toConfiguration(sectionObject, configuration);
        }
        return sections;
    }

    private YamlConfiguration getConfigFile(String path) throws IllegalAccessException, IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException {


        if (!FileUtility.pathExists(path)) {
            var yamlConfig = new YamlConfiguration();
            yamlMapper.toConfiguration(new DefaultConfigSection(plugin), yamlConfig);
            yamlConfig.save(path);
            return yamlConfig;
        }

        var file = new File(path);
        return  YamlConfiguration.loadConfiguration(file);
    }

}
