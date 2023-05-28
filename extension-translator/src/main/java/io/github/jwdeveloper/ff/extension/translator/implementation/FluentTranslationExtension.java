package io.github.jwdeveloper.ff.extension.translator.implementation;

import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslatorOptions;
import io.github.jwdeveloper.ff.extension.translator.implementation.commands.TranslatorCommand;
import io.github.jwdeveloper.ff.extension.translator.implementation.config.TranslatorConfig;
import io.github.jwdeveloper.ff.extension.translator.implementation.langs.SimpleTranslator;
import io.github.jwdeveloper.ff.extension.translator.implementation.langs.TranslationLoader;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPriority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;

import java.util.function.Consumer;

public class FluentTranslationExtension implements FluentApiExtension {
    private final Consumer<FluentTranslatorOptions> optionsConsumer;
    private final FluentTranslatorOptions options;
    private FluentTranslatorImpl fluentTranslator;


    public FluentTranslationExtension(Consumer<FluentTranslatorOptions> optionsConsumer) {
        this.optionsConsumer = optionsConsumer;
        options = new FluentTranslatorOptions();
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        optionsConsumer.accept(options);

        var translatorPath = builder.pluginPath().resolve(options.getTranslationsPath());
        FileUtility.ensurePath(translatorPath.toString());

        fluentTranslator = new FluentTranslatorImpl(new SimpleTranslator(builder.logger()), translatorPath);


        builder.bindToConfig(TranslatorConfig.class, options.getConfigPath());

        builder.container()
                .register(FluentTranslator.class, LifeTime.SINGLETON, (x) -> fluentTranslator);

        builder.permissions()
                .defaultPermissions()
                .getCommands().registerChild(p ->
                {
                    p.setName(options.getPermissionName());
                    p.setDescription("Change plugin language");
                });

        builder.defaultCommand()
                .subCommandsConfig(subCommandConfig ->
                {
                    var languageCmd = new TranslatorCommand(builder.defaultCommand(),
                            builder.config(),
                            fluentTranslator,
                            options);
                    subCommandConfig.addSubCommand(languageCmd.create());
                });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        var config = fluentAPI.container().findInjection(TranslatorConfig.class);

        var loader = new TranslationLoader(fluentAPI.plugin(), options);
        var language = config.getLanguage();
        var translations = loader.load(fluentTranslator.getTranslationsPath());

        fluentTranslator.addTranslationModel(translations);
        fluentTranslator.setLanguage(language);
        fluentTranslator.setDefaultLanguage("en");
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
