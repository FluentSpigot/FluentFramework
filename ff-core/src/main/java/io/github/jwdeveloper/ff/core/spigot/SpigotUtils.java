package io.github.jwdeveloper.ff.core.spigot;

import org.bukkit.Bukkit;

public class SpigotUtils {
    public static int INVENTORY_WIDTH = 9;

    public static boolean isMock() {
        return Bukkit.getServer().getClass().getSimpleName().contains("ServerMock");
    }
}
