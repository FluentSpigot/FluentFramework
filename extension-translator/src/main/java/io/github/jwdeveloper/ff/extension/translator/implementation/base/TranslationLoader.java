package io.github.jwdeveloper.ff.extension.translator.implementation.base;

import io.github.jwdeveloper.ff.core.common.ResourceSearch;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.files.yaml.implementation.YmlPathReader;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslatorOptions;
import io.github.jwdeveloper.ff.extension.translator.api.models.TranslationModel;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TranslationLoader {

    private final Plugin plugin;
    private final FluentTranslatorOptions options;

    public TranslationLoader(Plugin plugin,
                             FluentTranslatorOptions options) {
        this.plugin = plugin;
        this.options = options;
    }


    public List<TranslationModel> load(String folderPath) throws IOException, InvalidConfigurationException {
        var translations = mergeTranslationsToOneFiles(folderPath);
        var result = new ArrayList<TranslationModel>();
        var reader = new YmlPathReader();
        for (var entry : translations.entrySet())
        {
            var name = StringUtils.split(entry.getKey(), ".")[0];
            var langData = new TranslationModel();
            langData.setCountry(name);
            langData.setTranslations(reader.read(entry.getValue()));
            result.add(langData);
        }
        return result;
    }


    private HashMap<String, YamlConfiguration> mergeTranslationsToOneFiles(String outputPath) throws IOException, InvalidConfigurationException {
        var resources = ResourceSearch.getResourcesPaths(plugin);
        var reader = new YmlPathReader();
        var merged = new HashMap<String, YamlConfiguration>();

        for (var path : resources) {
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
        for (var result : merged.entrySet()) {
            result.getValue().save(outputPath + File.separator + result.getKey());
        }
        return merged;
    }


}
