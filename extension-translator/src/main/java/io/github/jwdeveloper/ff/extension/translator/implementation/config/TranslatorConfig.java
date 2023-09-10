package io.github.jwdeveloper.ff.extension.translator.implementation.config;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;
import lombok.Data;

@Data
public class TranslatorConfig {

    @YamlSection(name = "language", description = """
            If you want add your language open `languages` folder copy `en.yml`
            set `default-language` property to your file name and /reload server
            """)
    private String language;

    public String getLanguage()
    {

        if(StringUtils.isNullOrEmpty(language))
        {
            language = "en";
        }
       return language;
    }
}
