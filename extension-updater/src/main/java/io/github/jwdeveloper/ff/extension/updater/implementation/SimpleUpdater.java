package io.github.jwdeveloper.ff.extension.updater.implementation;

import io.github.jwdeveloper.ff.core.common.logger.PluginLogger;
import io.github.jwdeveloper.ff.core.common.versions.VersionCompare;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.spigot.events.api.FluentEventManager;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.ff.extension.updater.api.FluentUpdater;
import io.github.jwdeveloper.ff.extension.updater.api.UpdateInfoProvider;
import io.github.jwdeveloper.ff.extension.updater.api.info.CheckUpdateInfo;
import io.github.jwdeveloper.ff.extension.updater.api.info.UpdateInfoResponse;
import io.github.jwdeveloper.ff.extension.updater.implementation.services.FileDowloaderService;
import io.github.jwdeveloper.ff.extension.updater.implementation.services.MessagesSenderService;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class SimpleUpdater implements FluentUpdater {

    private final PluginLogger logger;
    private final UpdateInfoProvider provider;
    private final FluentTaskManager taskManager;
    private final MessagesSenderService messagesSenderService;
    private final FileDowloaderService fileDowloaderService;
    private final String currentVersion;

    public SimpleUpdater(UpdateInfoProvider provider,
                         FluentTaskManager taskManager,
                         PluginLogger logger,
                         MessagesSenderService messagesSenderService,
                         FileDowloaderService fileDowloaderService,
                         String currentVersion)
   {
        this.logger = logger;
       this.currentVersion = currentVersion;
        this.provider = provider;
        this.taskManager = taskManager;
        this.messagesSenderService = messagesSenderService;
        this.fileDowloaderService = fileDowloaderService;
    }



    @Override
    public void checkUpdateAsync(Consumer<CheckUpdateInfo> consumer) {
        taskManager.taskAsync(() ->
        {
            try {
                var updateInfo = checkUpdate();
                consumer.accept(updateInfo);
            } catch (Exception e) {
                logger.error("Checking for update error", e);
            }
        });
    }

    @Override
    public void checkUpdateAsync(CommandSender commandSender)
    {
        checkUpdateAsync(info ->
        {
            if (info.isUpdate()) {
                messagesSenderService.getMessagePrefix().text("Latest version is already downloaded").send(commandSender);
                return;
            }
            messagesSenderService.sendUpdateInfoMessage(commandSender, info.getUpdateInfo());
        });
    }


    @Override
    public CheckUpdateInfo checkUpdate() throws IOException {
        var infoResponse = provider.getUpdateInfo();
        if (VersionCompare.isHigher(infoResponse.getVersion(), currentVersion)) {
            return new CheckUpdateInfo(true, infoResponse);
        }
        return new CheckUpdateInfo(false, infoResponse);
    }


    @Override
    public void downloadUpdateAsync(CommandSender commandSender) {

        checkUpdateAsync(checkUpdateInfo ->
        {
            if (!checkUpdateInfo.isUpdate()) {
                messagesSenderService.getMessagePrefix().text("Latest version is already downloaded").send(commandSender);
                return;
            }
            messagesSenderService.getMessagePrefix().text("Downloading latest version...")
                    .send(commandSender);
            if (fileDowloaderService.download(checkUpdateInfo.getUpdateInfo())) {
                return;
            }
            messagesSenderService.getMessagePrefix()
                    .text("New version downloaded!")
                    .text("use /reload", ChatColor.AQUA).color(ChatColor.GRAY)
                    .text(" to apply changes")
                    .send(commandSender);
        });
    }






}
