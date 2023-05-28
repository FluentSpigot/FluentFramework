package io.github.jwdeveloper.ff.extension.translator.implementation.commands;

import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.CommandBuilder;
import io.github.jwdeveloper.ff.core.spigot.commands.api.enums.ArgumentDisplay;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslatorOptions;
import io.github.jwdeveloper.ff.plugin.api.config.FluentConfig;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.command.FluentApiCommandBuilder;
import org.bukkit.ChatColor;

public class TranslatorCommand {

    private final FluentApiCommandBuilder defaultCommand;
    private final FluentConfig configFile;
    private final FluentTranslatorOptions options;

    public TranslatorCommand(FluentApiCommandBuilder defaultCommand,
                             FluentConfig configFile,
                             FluentTranslatorOptions options) {
        this.defaultCommand = defaultCommand;
        this.configFile = configFile;
        this.options = options;
    }

    public CommandBuilder create() {
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
                        var translator = FluentApi.container().findInjection(FluentTranslator.class);
                        argumentBuilder.setTabComplete(translator.getLanguagesName());
                        argumentBuilder.setArgumentDisplay(ArgumentDisplay.TAB_COMPLETE);
                        argumentBuilder.setDescription("select language");
                    });
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onExecute(commandEvent ->
                    {
                        var translator = FluentApi.container().findInjection(FluentTranslator.class);
                        var languageName = commandEvent.getCommandArgs()[0];
                        if (!translator.isLanguageExists(languageName)) {
                            new MessageBuilder()
                                    .warning()
                                    .text(" Language ", ChatColor.GRAY)
                                    .text(languageName, ChatColor.RED)
                                    .text(" not found ", ChatColor.GRAY)
                                    .send(commandEvent.getSender());
                            return;
                        }
                        configFile.configFile().set(options.getConfigPath(), languageName);
                        configFile.save();
                        FluentApi.messages().chat().info()
                                .textSecondary(" Language has been changed to ")
                                .textPrimary(languageName)
                                .textSecondary(" use ")
                                .textPrimary("/reload")
                                .textSecondary(" to apply changes")
                                .send(commandEvent.getSender());
                    });
                });
    }
}
