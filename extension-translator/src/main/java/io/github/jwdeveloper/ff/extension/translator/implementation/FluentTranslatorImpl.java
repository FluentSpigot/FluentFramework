package io.github.jwdeveloper.ff.extension.translator.implementation;

import io.github.jwdeveloper.ff.core.common.logger.BukkitLogger;
import io.github.jwdeveloper.ff.extension.translator.api.models.LangData;
import io.github.jwdeveloper.ff.extension.translator.implementation.langs.SimpleLang;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import org.bukkit.entity.Player;

import java.nio.file.Path;
import java.util.List;

public class FluentTranslatorImpl implements FluentTranslator {
    private SimpleLang lang;
    private final BukkitLogger logger;
    private final String path;
    public FluentTranslatorImpl(BukkitLogger logger, Path path)
    {
        this.logger = logger;
        this.path = path.toString();
    }


    @Override
    public String getTranslationsPath() {
        return path;
    }

    @Override
    public String get(String key, Player player) {
        return null;
    }

    @Override
    public String get(String key) {
        return lang.get(key);
    }

    @Override
    public List<String> getLanguagesName() {
        return null;
    }

    @Override
    public String getPlayerLanguage(Player player) {
        return null;
    }

    @Override
    public boolean setDefaultLanguage(String name) {
        return lang.setLanguage(name);
    }

    @Override
    public boolean setPlayerLanguage(String name, Player player) {
        return false;
    }

    @Override
    public boolean isLanguageExists(String name) {
        return lang.langExists(name);
    }

    @Override
    public boolean isLanguageDefault(String name) {
        return false;
    }


    public void setLanguages(List<LangData> language, String name) {
        lang = new SimpleLang(language, logger);
        lang.setDefaultLang("en");
        lang.setLanguage(name);
    }
}
