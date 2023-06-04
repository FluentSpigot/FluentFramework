package io.github.jwdeveloper.ff.extension.updater.implementation;

import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.core.spigot.commands.api.builder.config.SubCommandConfig;
import io.github.jwdeveloper.ff.core.spigot.permissions.api.PermissionModel;
import io.github.jwdeveloper.ff.extension.updater.api.FluentUpdater;
import io.github.jwdeveloper.ff.extension.updater.api.UpdateInfoProvider;
import io.github.jwdeveloper.ff.extension.updater.api.UpdaterApiOptions;
import io.github.jwdeveloper.ff.extension.updater.api.config.UpdaterConfig;
import io.github.jwdeveloper.ff.extension.updater.api.options.GithubUpdaterOptions;
import io.github.jwdeveloper.ff.extension.updater.api.options.ProviderOptions;
import io.github.jwdeveloper.ff.extension.updater.implementation.providers.GithubInfoProvider;
import io.github.jwdeveloper.ff.extension.updater.implementation.services.FileDowloaderService;
import io.github.jwdeveloper.ff.extension.updater.implementation.services.MessagesSenderService;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import org.bukkit.Bukkit;

import java.util.function.Consumer;

public class FluentUpdaterExtension implements FluentApiExtension {
    private final Consumer<UpdaterApiOptions> optionsConsumer;
    private ProviderOptions providerOptions;

    private UpdaterApiOptions updaterApiOptions;

    public FluentUpdaterExtension(Consumer<UpdaterApiOptions> options) {
        optionsConsumer = options;
    }

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        updaterApiOptions = new UpdaterApiOptions();
        optionsConsumer.accept(updaterApiOptions);
        providerOptions = updaterApiOptions.getProviderOptions();


        builder.bindToConfig(UpdaterConfig.class, updaterApiOptions.getConfigPath());

        builder.container().registerTransient(FileDowloaderService.class);
        builder.container().registerTransient(MessagesSenderService.class);
        builder.container().register(FluentUpdater.class, LifeTime.SINGLETON, (c) ->
        {
            UpdateInfoProvider updateProvider = null;
            if (providerOptions instanceof GithubUpdaterOptions githubUpdaterOptions) {
                updateProvider = new GithubInfoProvider(githubUpdaterOptions);
            }

            var fileService = (FileDowloaderService) c.find(FileDowloaderService.class);
            var messageService = (MessagesSenderService) c.find(MessagesSenderService.class);

            return new SimpleUpdater(
                    updateProvider,
                    builder.tasks(),
                    builder.logger(),
                    messageService,
                    fileService,
                    builder.plugin().getDescription().getVersion());
        });


        builder.permissions().registerPermission(this::createPermission);
        builder.defaultCommand().subCommandsConfig(x -> createCommand(x, builder.defaultCommand().getName()));
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {
        var updater = fluentAPI.container().findInjection(FluentUpdater.class);
        var config = fluentAPI.container().findInjection(UpdaterConfig.class);

        if (config.isForceUpdate())
            updater.downloadUpdateAsync(Bukkit.getConsoleSender());

        if (config.getCheckUpdateConfig().isCheckUpdate())
        {
            updater.checkUpdateAsync(Bukkit.getConsoleSender());
        }

    }


    private void createCommand(SubCommandConfig config, String defaultCommandName) {
        config.addSubCommand(updaterApiOptions.getCommandName(), commandBuilder ->
        {
            commandBuilder.propertiesConfig(propertiesConfig ->
                    {
                        propertiesConfig.addPermissions(updaterApiOptions.getPermissionName());
                        propertiesConfig.setDescription("Download plugin latest version, can be trigger both by player or console");
                        propertiesConfig.setUsageMessage("/" + defaultCommandName + " " + updaterApiOptions.getCommandName());
                    })
                    .eventsConfig(eventConfig ->
                    {
                        eventConfig.onExecute(consoleCommandEvent ->
                        {
                            var updater = FluentApi.container().findInjection(FluentUpdater.class);
                            updater.downloadUpdateAsync(consoleCommandEvent.getSender());
                        });
                    });
        });
    }

    private void createPermission(PermissionModel model) {

        model.setName(updaterApiOptions.getPermissionName());
        model.setDescription("Players with this permission can update plugin");
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String getName() {
        return "updater";
    }
}
