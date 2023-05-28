package io.github.jwdeveloper.ff.extension.translator.api;


import io.github.jwdeveloper.ff.extension.translator.api.models.TranslationModel;
import org.bukkit.entity.Player;

import java.util.List;

public interface FluentTranslator
{
    String getTranslationsPath();
    String get(String key);
    boolean setLanguage(String name);
    boolean setDefaultLanguage(String name);
    boolean isCurrentLanguage(String name);
    boolean isLanguageExists(String name);
    List<String> getLanguagesName();
    void addTranslationModel(List<TranslationModel> translationModels);
}
