package io.github.jwdeveloper.ff.extension.translator.implementation.commands;

import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.SimpleCommandBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.ArgumentDisplay;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslatorOptions;
import io.github.jwdeveloper.ff.extension.translator.implementation.base.LanguagesDictionary;
import io.github.jwdeveloper.ff.extension.translator.implementation.config.TranslatorConfig;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLogger;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.command.FluentApiCommandBuilder;
import org.bukkit.ChatColor;

public class TranslatorCommand {

    private final FluentApiCommandBuilder defaultCommand;
    private final FluentConfig configFile;
    private final FluentTranslatorOptions options;
    private final LanguagesDictionary languagesDictionary;


    public TranslatorCommand(FluentApiCommandBuilder defaultCommand,
                             FluentConfig configFile,
                             FluentTranslatorOptions options,
                             LanguagesDictionary languagesDictionary) {
        this.defaultCommand = defaultCommand;
        this.configFile = configFile;
        this.options = options;
        this.languagesDictionary = languagesDictionary;

    }

    public SimpleCommandBuilder create() {
        return FluentApi.createCommand(options.getCommandName())
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.addPermissions(options.getPermissionName());
                    propertiesConfig.setDescription("Changes plugin languages, changes will be applied after server reload. Change be use both be player or console");
                    propertiesConfig.setUsageMessage("/" + defaultCommand.getName() + " " + options.getCommandName() + " <language>");
                })
                .argumentsConfig(argumentConfig ->
                {
                    argumentConfig.addArgument("language", argumentBuilder ->
                    {
                        argumentBuilder.setTabComplete(languagesDictionary::getCountryNames);
                        argumentBuilder.setArgumentDisplay(ArgumentDisplay.TAB_COMPLETE);
                        argumentBuilder.setDescription("select language");
                    });
                })
                .eventsConfig(eventConfig ->
                {
                    var translator = FluentApi.container().findInjection(FluentTranslator.class);
                    eventConfig.onExecute(commandEvent ->
                    {
                        var countryName = commandEvent.getCommandArgs()[0];
                        var countryCode = languagesDictionary.getCountryCode(countryName);

                        if (!translator.isLanguageExists(countryCode)) {
                            translator.generate(commandEvent.getSender(), countryCode);
                            return;
                        }

                        var wrapper = FluentApi.container().findInjection(FluentConfigFile.class, TranslatorConfig.class);
                        var config = (TranslatorConfig)wrapper.get();

                        config.setLanguage(countryCode);
                        wrapper.save();

                        FluentApi.container()
                                .findInjection(PlayerLogger.class)
                                .info("Language has been changed to")
                                .text(ChatColor.AQUA, countryName, ChatColor.RESET) .send(commandEvent.getSender());
                        FluentApi.container()
                                .findInjection(PlayerLogger.class)
                                .info("use /reload to apply changes")
                                .send(commandEvent.getSender());
                    });
                });
    }
}
