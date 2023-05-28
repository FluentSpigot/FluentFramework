package io.github.jwdeveloper.ff.extension.translator.api.models;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class TranslationModel
{
    private String country = "undefined";

    private Map<String, String> translations = new ConcurrentHashMap<>();
}
