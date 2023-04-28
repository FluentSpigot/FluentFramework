package io.github.jwdeveloper.ff.core.translator.api.models;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class LangData
{
    private String country = "undefined";

    private Map<String, String> translations = new ConcurrentHashMap<>();
}
