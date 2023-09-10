package io.github.jwdeveloper.ff.extension.updater.implementation.services;

import io.github.jwdeveloper.ff.core.logger.plugin.PluginLogger;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.extension.updater.api.info.UpdateInfoResponse;
import org.bukkit.plugin.Plugin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class FileDowloaderService
{
    private final Plugin plugin;
    private final PluginLogger logger;

    public FileDowloaderService(Plugin plugin, PluginLogger logger)
    {
        this.logger = logger;
        this.plugin = plugin;
    }

    public boolean download(UpdateInfoResponse info) {
        var output = getUpdatesFolder() + info.getFileName();
        FileUtility.ensurePath(output);
        var downloadUrl = info.getDownloadUrl();
        try {
            var in = new BufferedInputStream(new URL(downloadUrl).openStream());
            var file = new File(output);
            file.createNewFile();
            var fileOutputStream = new FileOutputStream(output, false);
            var dataBuffer = new byte[1024];
            var bytesRead = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            logger.error("Update download error", e);
            return false;
        }
    }

    private String getUpdatesFolder() {
        return FileUtility.pluginPath(plugin) + File.separator + "update" + File.separator;
    }

}
