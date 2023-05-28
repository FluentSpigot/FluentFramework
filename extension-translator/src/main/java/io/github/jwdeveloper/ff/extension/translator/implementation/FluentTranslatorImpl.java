package io.github.jwdeveloper.ff.extension.translator.implementation;

import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.extension.translator.api.models.TranslationModel;
import io.github.jwdeveloper.ff.extension.translator.implementation.base.SimpleTranslator;
import io.github.jwdeveloper.ff.extension.translator.implementation.config.TranslatorConfig;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.ConfigOptions;

import java.nio.file.Path;
import java.util.List;

public class FluentTranslatorImpl implements FluentTranslator {
    private final SimpleTranslator translator;
    private final String path;
    private final ConfigOptions<TranslatorConfig> options;

    public FluentTranslatorImpl(Path path,
                                SimpleTranslator lang,
                                ConfigOptions<TranslatorConfig> options) {
        this.path = path.toString();
        this.translator = lang;
        this.options = options;
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
        return translator.setCurrentTranslation(name);
    }


    @Override
    public boolean isCurrentLanguage(String name) {
        return translator.isCurrentLanguage(name);
    }

    @Override
    public boolean isLanguageExists(String name) {
        return translator.isTranslationExists(name);
    }

}
