package io.github.jwdeveloper.ff.core.common;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PluginUtils
{
    public static boolean isMockPlugin(Plugin plugin)
    {
        return plugin.getClass().getSimpleName().contains("MockPlugin");
    }

    public static File pluginFile(JavaPlugin plugin) {
        try {
            var fileField = JavaPlugin.class.getDeclaredField("file");
            fileField.setAccessible(true);
            return (File) fileField.get(plugin);
        } catch (Exception e) {
            throw new RuntimeException("Can not load plugin file",e);
        }
    }

}
