package io.github.jwdeveloper.ff.extension.translator.implementation.base;

import io.github.jwdeveloper.ff.core.common.logger.BukkitLogger;
import io.github.jwdeveloper.ff.extension.translator.api.models.TranslationModel;
import lombok.Getter;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;


public class SimpleTranslator {

    private final String LANGUAGE_NOT_SELECTED = ChatColor.RED + "LANGUAGE NOT SELECTED";
    private final String NOT_FOUND = ChatColor.RED + "TRANSLATION NOT FOUND";

    private final BukkitLogger logger;

    @Getter
    private final List<TranslationModel> languages;

    private TranslationModel currentLang;

    private TranslationModel defaultLang;


    public SimpleTranslator(BukkitLogger logger) {
        this.logger = logger;
        languages = new ArrayList<>();
    }

    public String get(String key) {
        if (currentLang == null || defaultLang == null) {
            return LANGUAGE_NOT_SELECTED;
        }




        if (currentLang.getTranslations().containsKey(key)) {
            return currentLang.getTranslations().get(key);
        }

        if (defaultLang.getTranslations().containsKey(key)) {
            return defaultLang.getTranslations().get(key);
        }

        logger.warning(NOT_FOUND + ": " + key);
        return NOT_FOUND;
    }


    public boolean setDefaultTranslation(String name) {
        for (var language : languages) {
            if (language.getCountry().equals(name)) {
                defaultLang = language;
                return true;
            }
        }
        logger.warning("Language not found: " + name);
        return false;
    }


    public boolean setCurrentTranslation(String name) {
        for (var language : languages) {
            if (language.getCountry().equals(name))
            {
                currentLang = language;
                if(defaultLang == null)
                {
                    defaultLang = language;
                }
                return true;
            }
        }
        logger.warning("Language not found: " + name);
        return false;
    }


    public void addTranslationModel(List<TranslationModel> translationModels) {
        languages.addAll(translationModels);
    }

    public boolean isTranslationExists(String name) {
        return languages.stream().anyMatch(c -> c.getCountry().equals(name));
    }

    public boolean isCurrentLanguage(String name) {
        if (currentLang == null) {
            return false;
        }
        if (!currentLang.getCountry().equals(name)) {
            return false;
        }
        return true;
    }

}
