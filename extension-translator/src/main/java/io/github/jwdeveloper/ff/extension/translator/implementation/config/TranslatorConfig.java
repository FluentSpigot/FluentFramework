package io.github.jwdeveloper.ff.extension.translator.implementation.config;

import io.github.jwdeveloper.ff.core.files.yaml.api.annotations.YamlSection;

@YamlSection(path = "language")
public class TranslatorConfig {


    @YamlSection(name = "user-name")
    private String name = "mike";

}
