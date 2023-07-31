package io.github.jwdeveloper.ff.extension.translator.implementation.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguagesDictionary {
    private final Map<String, String> languagesNames;

    public LanguagesDictionary()
    {
        languagesNames = generate();
    }

    public List<String> getCountryNames()
    {
        return languagesNames.keySet().stream().toList();
    }

    public List<String> getCountryCodes()
    {
        return languagesNames.values().stream().toList();
    }

    public String getCountryCode(String countryName)
    {
        return languagesNames.get(countryName);
    }

    private Map<String, String> generate() {
        var result = new HashMap<String, String>();
        result.put("english", "en");
        result.put("polish", "pl");
        result.put("french", "fr");
        result.put("german", "de");
        result.put("korean", "ko");
        result.put("russian", "ru");
        result.put("spanish", "es");
        result.put("turkish", "tr");
        result.put("italian", "it");
        result.put("portugal", "pt");
        result.put("swedish", "se");
        return result;
    }
}
