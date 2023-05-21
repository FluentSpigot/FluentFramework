package io.github.jwdeveloper.ff.extension.translator.implementation;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.common.logger.SimpleLogger;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.core.spigot.permissions.api.PermissionModel;
import io.github.jwdeveloper.ff.core.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.core.translator.implementation.SimpleLangLoader;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslatorOptions;
import io.github.jwdeveloper.ff.extension.translator.implementation.commands.LanguageCommand;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.config.ConfigProperty;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.ff.plugin.api.extention.ExtentionPriority;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.permissions.api.FluentPermissionBuilder;

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
    public ExtentionPriority getPriority() {
        return ExtentionPriority.LOW;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        optionsConsumer.accept(options);

        var translatorPath = builder.pluginPath().resolve(options.getTranslationsPath());
        FileUtility.ensurePath(translatorPath.toString());

        fluentTranslator = new FluentTranslatorImpl(builder.logger(), translatorPath);

        builder.container()
               .register(FluentTranslator.class, LifeTime.SINGLETON, (x) -> fluentTranslator);

        builder.permissions()
                .registerPermission(createPermission(builder.permissions()));

        builder.defaultCommand()
                .subCommandsConfig(subCommandConfig ->
                {
                    var languageCmd = new LanguageCommand();
                    var command = languageCmd.create();

                    subCommandConfig.addSubCommand(command);
                });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        var loader = new SimpleLangLoader(fluentAPI.plugin());

        loader.generateFiles(fluentTranslator.getTranslationsPath());
        var langName = getPluginLanguage(fluentAPI.config(), fluentAPI.logger());
        var langDatas = loader.load(fluentTranslator.getTranslationsPath(), langName);
        fluentTranslator.setLanguages(langDatas, langName);

        // fluentTranslator.generateEmptyTranlations();
    }

    private String getPluginLanguage(FluentConfig configFile, SimpleLogger logger) {

        var configProperty = createConfigLanguage();
        var languageValue = configFile.getOrCreate(configProperty);
        if (StringUtils.isNullOrEmpty(languageValue)) {
            logger.warning("Unable to load `" + options.getConfigPath() + "` from config");
            return "en";
        }
        return languageValue;
    }


    private PermissionModel createPermission(FluentPermissionBuilder builder) {
        var permission = new PermissionModel();
        permission.setName(options.getPermissionName());
        permission.setDescription("Allow player to change plugin language");
        builder.defaultPermissionSections().commands().addChild(permission);
        return permission;
    }


    private ConfigProperty<String> createConfigLanguage() {
        return new ConfigProperty<String>(options.getConfigPath(), "en",
                "If you want add your language open `languages` folder copy `en.yml` call it as you want \\n\" +\n" +
                        " \"set `language` property to your file name and /reload server ");
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
