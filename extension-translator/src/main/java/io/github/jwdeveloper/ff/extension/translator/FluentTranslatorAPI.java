package io.github.jwdeveloper.ff.extension.translator;

import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslatorOptions;
import io.github.jwdeveloper.ff.extension.translator.implementation.FluentTranslationExtension;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;

import java.util.function.Consumer;

public class FluentTranslatorAPI
{
    public static FluentApiExtension useTranslator(Consumer<FluentTranslatorOptions> options) {
        return new FluentTranslationExtension(options);
    }

    public static FluentApiExtension useTranslator() {
        return useTranslator(e -> {});
    }
}
