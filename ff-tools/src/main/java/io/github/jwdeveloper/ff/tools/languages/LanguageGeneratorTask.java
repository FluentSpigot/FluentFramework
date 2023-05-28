package io.github.jwdeveloper.ff.tools.languages;

import io.github.jwdeveloper.ff.core.common.ResourceSearch;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.regex.Pattern;

public class LanguageGeneratorTask
{
    public static void run(String outputPath)
    {
        var list = ResourceSearch.getResourcesPaths(Pattern.compile("^.*\\.(yml)$"));
        YamlConfiguration configuration = null;
        for (var name : list) {
            if (!name.contains("template")) {
                continue;
            }
            var temp = YamlConfiguration.loadConfiguration(new File(name));
            if(configuration == null)
            {
                configuration = temp;
                continue;
            }
            mergeSections(configuration,temp);
        }


        try {
            var filePath = outputPath+"\\en.yml";
            configuration.save(filePath);

            var options = new TranslationsGenerator.Options();
            options.setInputPath(outputPath);
            TranslationsGenerator.run(options);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static void mergeSections(ConfigurationSection parent, ConfigurationSection child)
    {
        for(var key : child.getKeys(false))
        {
            Object value = null;
            if(child.isConfigurationSection(key))
            {
                value = child.getConfigurationSection(key);
            }
            else
            {
                value = child.get(key);
            }

            if(parent.isConfigurationSection(key))
            {
                var a = parent.getConfigurationSection(key);
                var b= child.getConfigurationSection(key);
                mergeSections(a,b);
            }
            else
            {
                parent.set(key,value);
            }
        }
    }
}
