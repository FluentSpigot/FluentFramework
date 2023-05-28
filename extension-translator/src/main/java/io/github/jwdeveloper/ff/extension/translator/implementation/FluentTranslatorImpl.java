package io.github.jwdeveloper.ff.extension.translator.implementation;

import io.github.jwdeveloper.ff.extension.translator.api.models.TranslationModel;
import io.github.jwdeveloper.ff.extension.translator.implementation.langs.SimpleTranslator;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;

import java.nio.file.Path;
import java.util.List;

public class FluentTranslatorImpl implements FluentTranslator {
    private final SimpleTranslator translator;
    private final String path;
    public FluentTranslatorImpl(SimpleTranslator lang, Path path)
    {
        this.path = path.toString();
        this.translator = lang;
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
        return translator.setCurrentTranslation(name);
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
