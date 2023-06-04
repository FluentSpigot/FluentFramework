package io.github.jwdeveloper.ff.extension.resourcepack.api.models;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public record ResourceSoundModel(String ymlName, String name, float volume, float pitch) {

    public void play(Player player) {
        play(player, player.getLocation());
    }

    public void play(Player player, Location location) {
        player.playSound(location, name,volume,pitch);
    }

    public void play(Location location) {
        location.getWorld().playSound(location, name, volume, pitch);
    }

}
