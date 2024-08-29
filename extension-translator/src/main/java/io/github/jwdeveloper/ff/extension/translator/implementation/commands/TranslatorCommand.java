package io.github.jwdeveloper.ff.extension.translator.implementation.commands;

import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslatorOptions;
import io.github.jwdeveloper.ff.extension.translator.implementation.base.LanguagesDictionary;
import io.github.jwdeveloper.ff.extension.translator.implementation.config.TranslatorConfig;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.ff.plugin.api.logger.PlayerLogger;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.FluentConfigFile;

import io.github.jwdeveloper.spigot.commands.api.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.api.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.api.data.DisplayAttribute;
import org.bukkit.ChatColor;

public class TranslatorCommand {

    private final FluentConfig configFile;
    private final FluentTranslatorOptions options;
    private final LanguagesDictionary languagesDictionary;


    public TranslatorCommand(
            FluentConfig configFile,
            FluentTranslatorOptions options,
            LanguagesDictionary languagesDictionary) {
        this.configFile = configFile;
        this.options = options;
        this.languagesDictionary = languagesDictionary;

    }

    public CommandBuilder decorate(CommandBuilder builder) {

        builder.properties().name(options.getCommandName());
        builder.withPermission(options.getPermission());
        builder.withDescription("Changes plugin languages, changes will be applied after server reload. Change be use both be player or console");
        builder.withUsageMessage("/" + options.getCommandName() + " " + options.getCommandName() + " <language>");


        builder.addArgument("language", argBuilder ->
        {
            argBuilder.withSuggestions(event -> ActionResult.success(languagesDictionary.getCountryNames()));
            argBuilder.withDisplayAttribute(DisplayAttribute.SUGGESTIONS);
            argBuilder.withDescription("Select language");
        });

        builder.onExecute(event ->
        {

            var sender = event.sender();
            var translator = event.command().container().find(FluentTranslator.class);
            var countryName = event.getString("language");
            var countryCode = languagesDictionary.getCountryCode(countryName);

            if (!translator.isLanguageExists(countryCode)) {
                translator.generate(sender, countryCode);
                return;
            }

            var wrapper = FluentApi.container().findInjection(FluentConfigFile.class, TranslatorConfig.class);
            var config = (TranslatorConfig) wrapper.get();

            config.setLanguage(countryCode);
            wrapper.save();

            FluentApi.container()
                    .findInjection(PlayerLogger.class)
                    .info("Language has been changed to")
                    .text(ChatColor.AQUA, countryName, ChatColor.RESET).send(sender);
            FluentApi.container()
                    .findInjection(PlayerLogger.class)
                    .info("use /reload to apply changes")
                    .send(sender);
        });
        return builder;
    }

//        return FluentApi.createCommand(options.getCommandName())
//                .propertiesConfig(propertiesConfig ->
//                {
//                    propertiesConfig.addPermissions(options.getPermission());
//                    propertiesConfig.setDescription("Changes plugin languages, changes will be applied after server reload. Change be use both be player or console");
//                    propertiesConfig.setUsageMessage("/" + builder.getName() + " " + options.getCommandName() + " <language>");
//                })
//                .argumentsConfig(argumentConfig ->
//                {
//                    argumentConfig.addArgument("language", argumentBuilder ->
//                    {
//                        argumentBuilder.setTabComplete(languagesDictionary::getCountryNames);
//                        argumentBuilder.setArgumentDisplay(ArgumentDisplay.TAB_COMPLETE);
//                        argumentBuilder.setDescription("select language");
//                    });
//                })
//                .eventsConfig(eventConfig ->
//                {
//                    eventConfig.onExecute(commandEvent ->
//                    {
//                        var countryName = commandEvent.getCommandArgs()[0];
//                        var countryCode = languagesDictionary.getCountryCode(countryName);
//
//                        if (!translator.isLanguageExists(countryCode)) {
//                            translator.generate(commandEvent.getSender(), countryCode);
//                            return;
//                        }
//
//                        var wrapper = FluentApi.container().findInjection(FluentConfigFile.class, TranslatorConfig.class);
//                        var config = (TranslatorConfig) wrapper.get();
//
//                        config.setLanguage(countryCode);
//                        wrapper.save();
//
//                        FluentApi.container()
//                                .findInjection(PlayerLogger.class)
//                                .info("Language has been changed to")
//                                .text(ChatColor.AQUA, countryName, ChatColor.RESET).send(commandEvent.getSender());
//                        FluentApi.container()
//                                .findInjection(PlayerLogger.class)
//                                .info("use /reload to apply changes")
//                                .send(commandEvent.getSender());
//                    });
//                });

}
