package io.github.jwdeveloper.ff.extension.translator.implementation;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslatorOptions;
import io.github.jwdeveloper.ff.extension.translator.implementation.base.LanguagesDictionary;
import io.github.jwdeveloper.ff.extension.translator.implementation.base.SimpleTranslator;
import io.github.jwdeveloper.ff.extension.translator.implementation.base.TranslationLoader;
import io.github.jwdeveloper.ff.extension.translator.implementation.commands.TranslatorCommand;
import io.github.jwdeveloper.ff.extension.translator.implementation.config.TranslatorConfig;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPriority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLogger;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;

import java.util.function.Consumer;

public class FluentTranslationExtension implements FluentApiExtension {
    private final Consumer<FluentTranslatorOptions> optionsConsumer;
    private final FluentTranslatorOptions options;

    private final LanguagesDictionary languagesDictionary;

    public FluentTranslationExtension(Consumer<FluentTranslatorOptions> optionsConsumer) {
        this.optionsConsumer = optionsConsumer;
        options = new FluentTranslatorOptions();
        languagesDictionary = new LanguagesDictionary();
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        optionsConsumer.accept(options);

        var translatorPath = builder.pluginPath().resolve(options.getTranslationsPath());
        FileUtility.ensurePath(translatorPath.toString());

        builder.bindToConfig(TranslatorConfig.class, options.getConfigPath());
        builder.container().registerSingleton(LanguagesDictionary.class, languagesDictionary);
        builder.container().registerSingleton(FluentTranslator.class, (x) ->
        {
            var logger = (PlayerLogger) x.find(PlayerLogger.class);
            var config = (FluentConfigFile<TranslatorConfig>) x.find(FluentConfigFile.class, TranslatorConfig.class);
            var simpleTranslator = new SimpleTranslator(builder.logger());
            return new FluentTranslatorImpl(translatorPath, simpleTranslator, config, logger);
        });

        builder.permissions()
                .defaultPermissions()
                .getCommands().registerChild(p ->
                {
                    p.setName(options.getPermission());
                    p.setDescription("Change plugin language");
                });

        if (options.isAddCommands()) {

            var languageCommandDecorator = new TranslatorCommand(
                    builder.config(),
                    options,
                    languagesDictionary);

            builder.mainCommand().addSubCommand(options.getCommandName(), languageCommandDecorator::decorate);
        }
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

        var translator = fluentAPI.container().findInjection(FluentTranslator.class);
        var config = fluentAPI.container().findInjection(TranslatorConfig.class);

        var loader = new TranslationLoader(fluentAPI.plugin(), options);
        var language = config.getLanguage();
        if (StringUtils.isNullOrEmpty(language)) {
            config.setLanguage("en");
            language = "en";
        }

        var translationsPath = translator.getTranslationsPath();
        var translations = loader.loadTranslations(translationsPath);
        translator.addTranslationModel(translations);
        translator.setLanguage(language);
        translator.setDefaultLanguage("en");
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getName() {
        return "translator";
    }

    @Override
    public ExtentionPriority getPriority() {
        return ExtentionPriority.LOW;
    }
}
