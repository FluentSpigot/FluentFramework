package io.github.jwdeveloper.ff.extension.translator.implementation;

import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.extension.translator.api.models.TranslationModel;
import io.github.jwdeveloper.ff.extension.translator.implementation.base.SimpleTranslator;
import io.github.jwdeveloper.ff.extension.translator.implementation.config.TranslatorConfig;
import io.github.jwdeveloper.ff.extension.translator.implementation.generator.TranslationsGenerator;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLogger;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;
import org.bukkit.command.CommandSender;

import java.nio.file.Path;
import java.util.List;

public class FluentTranslatorImpl implements FluentTranslator {
    private final SimpleTranslator translator;
    private final String path;
    private final FluentConfigFile<TranslatorConfig> options;
    private final PlayerLogger playerLogger;

    public FluentTranslatorImpl(Path path,
                                SimpleTranslator lang,
                                FluentConfigFile<TranslatorConfig> options, PlayerLogger playerLogger) {
        this.path = path.toString();
        this.translator = lang;
        this.options = options;
        this.playerLogger = playerLogger;
    }

    @Override
    public String getTranslationsPath() {
        return path;
    }

    @Override
    public String get(String key) {
        return translator.get(key);
    }

    @Override
    public String get(String key, Object... params) {
        FluentLogger.LOGGER.info("TO DO PARAMS IN TRANSLATOR");
        return get(key);
    }

    @Override
    public List<String> getLanguagesName() {
        return translator.getLanguages().stream().map(TranslationModel::getCountry).toList();
    }

    @Override
    public void addTranslationModel(List<TranslationModel> translationModels) {
        translator.addTranslationModel(translationModels);
    }

    @Override
    public boolean setLanguage(String name) {


        if (!translator.setCurrentTranslation(name)) {
            return false;
        }
        options.get().setLanguage(name);
        options.save();
        return true;
    }

    @Override
    public boolean setDefaultLanguage(String name) {
        return translator.setDefaultTranslation(name);
    }


    @Override
    public boolean isCurrentLanguage(String name) {
        return translator.isCurrentLanguage(name);
    }

    @Override
    public boolean isLanguageExists(String name) {
        return translator.isTranslationExists(name);
    }


    public void generate(CommandSender commandSender, String name) {

        if(name.equals("en"))
        {
            return;
        }

        var options = new TranslationsGenerator.Options();
        options.setFromLanguage("en");
        options.setLanguageToTranslate(List.of(name));
        options.setInputPath(path);

        FluentApi.tasks().taskAsync(() ->
        {
            playerLogger.info("Generating translations for language: "+name).send(commandSender);
            try {
                TranslationsGenerator.run(options,s ->
                {
                    playerLogger.info("Translations generated, reload server to see new language").send(commandSender);
                });
            } catch (Exception e)
            {
                playerLogger.error(e,"Unable to generate language",name);
            }
        });

    }
}
