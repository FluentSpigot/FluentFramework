package io.github.jwdeveloper.ff.extension.translator.implementation.base;

import io.github.jwdeveloper.ff.core.common.ResourceSearch;
import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.files.yaml.implementation.YmlPathReader;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslatorOptions;
import io.github.jwdeveloper.ff.extension.translator.api.models.TranslationModel;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class TranslationLoader {

    private final Plugin plugin;
    private final FluentTranslatorOptions options;

    public TranslationLoader(Plugin plugin,
                             FluentTranslatorOptions options) {
        this.plugin = plugin;
        this.options = options;
    }


    public List<TranslationModel> loadTranslations(String folderPath) throws IOException, InvalidConfigurationException {
        var pluginTranslations = loadFromPluginResources(folderPath);
        var folderTranslations = loadFromLanguagesFolder(folderPath);

        FluentLogger.LOGGER.info("pluginTranslations",pluginTranslations.size());
        FluentLogger.LOGGER.info("folderTranslations",folderTranslations.size());

        pluginTranslations.addAll(folderTranslations);
        return pluginTranslations;
    }


    private List<TranslationModel> loadFromPluginResources(String outputPath) throws IOException, InvalidConfigurationException {
        var resources = ResourceSearch.getResourcesPaths(plugin);
        var translations = createTranslation(resources);
        for (var result : translations.entrySet()) {
            result.getValue().save(outputPath + File.separator + result.getKey());
        }
        var result = new ArrayList<TranslationModel>();
        var reader = new YmlPathReader();
        for (var entry : translations.entrySet()) {
            var name = StringUtils.split(entry.getKey(), ".")[0];
            var langData = new TranslationModel();
            langData.setCountry(name);
            langData.setTranslations(reader.read(entry.getValue()));
            result.add(langData);
        }
        return result;
    }

    private List<TranslationModel> loadFromLanguagesFolder(String inputPath) throws IOException, InvalidConfigurationException {
        var resources = FileUtility.getFolderFilesName(inputPath, "yml");

        var reader = new YmlPathReader();
        var merged = new HashMap<String, YamlConfiguration>();
        for (var path : resources) {
            var fileName = FileUtility.getPathFileName(path);
            File initialFile = new File(inputPath + FileUtility.separator()+path);
            InputStream targetStream = new FileInputStream(initialFile);
            var generated = reader.read(targetStream);
            targetStream.close();
            var configuration = new YamlConfiguration();
            if (merged.containsKey(fileName)) {
                configuration = merged.get(fileName);
            } else {
                merged.put(fileName, configuration);
            }

            for (var line : generated.entrySet()) {
                configuration.set(line.getKey(), line.getValue());
            }
        }


        var result = new ArrayList<TranslationModel>();
        for (var entry : merged.entrySet()) {
            var name = StringUtils.split(entry.getKey(), ".")[0];
            var langData = new TranslationModel();
            langData.setCountry(name);
            langData.setTranslations(reader.read(entry.getValue()));
            result.add(langData);
        }
        return result;
    }


    private Map<String, YamlConfiguration> createTranslation(Collection<String> paths) throws IOException, InvalidConfigurationException {
        var reader = new YmlPathReader();
        var merged = new HashMap<String, YamlConfiguration>();

        for (var path : paths) {
            if (!path.contains(options.getTranslationsPath())) {
                continue;
            }
            var fileName = FileUtility.getPathFileName(path);
            var resource = getClass().getClassLoader().getResourceAsStream(path);
            var generated = reader.read(resource);

            var configuration = new YamlConfiguration();
            if (merged.containsKey(fileName)) {
                configuration = merged.get(fileName);
            } else {
                merged.put(fileName, configuration);
            }

            for (var line : generated.entrySet()) {
                configuration.set(line.getKey(), line.getValue());
            }
        }
        return merged;
    }


}
