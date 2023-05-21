package io.github.jwdeveloper.ff.extension.translator.implementation;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;

public class TranslationsGenerator
{

    public void generateEmptyTranlations() {
        logger.info("Generating empty tranlations");
        var Optional = getLanguages().stream().filter(f -> f.getCountry().equals("en")).findFirst();
        var eng = Optional.get();


        var filePath = path+ FileUtility.separator()+"temp";
        FileUtility.ensurePath(filePath);
        for(var lang : getLanguages())
        {
            if(eng.getCountry().equals(lang.getCountry()))
            {
                continue;
            }
            FluentLogger.LOGGER.info("========================= Language",lang.getCountry()," =================================");
            FluentLogger.LOGGER.info("Tital paths", lang.getTranslations().size());
            var configuration = new YamlConfiguration();
            for(var yamlPath : eng.getTranslations().keySet())
            {

                var value = StringUtils.EMPTY;
                if(lang.getTranslations().containsKey(yamlPath))
                {
                    FluentLogger.LOGGER.success("Language",lang.getCountry(),yamlPath);
                    value = lang.getTranslations().get(yamlPath);
                }
                else
                {
                    FluentLogger.LOGGER.warning("Language",lang.getCountry(),yamlPath);
                    value = "<!EMPTY!> " +eng.getTranslations().get(yamlPath);
                }
                configuration.set(yamlPath, value);
            }
            var path2 = filePath+ FileUtility.separator()+lang.getCountry()+".yml";
            configuration.save(path2);
        }
        logger.success("Generating done");
    }
}
