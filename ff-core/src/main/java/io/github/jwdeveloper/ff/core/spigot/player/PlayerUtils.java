package io.github.jwdeveloper.ff.core.spigot.player;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class PlayerUtils {


    public static float getBodyYaw(Location from, Location to)
    {
        return new BodyRotation().tickMovement(from,to);
    }
    public static void playSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1, 1);
    }

    public static void playSound(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public static boolean giveItem(Player player, ItemStack itemStack, Consumer<Player> ifThereIsNoSlot) {
        var slot = getEmptyEquipmentSlot(player);
        if (slot == -1) {
            ifThereIsNoSlot.accept(player);
            return false;
        }
        player.getInventory().setItem(slot, itemStack);
        return true;
    }

    public static int getEmptyEquipmentSlot(Player player) {
        var inventory = player.getInventory();

        var index = 0;
        var items = inventory.getStorageContents();
        for (var i = 0; i < items.length; i++) {
            if (items[i] == null) {
                return i;
            }
            if (items[i].getType() == Material.AIR) {
                return i;
            }
        }
        return -1;
    }
}
