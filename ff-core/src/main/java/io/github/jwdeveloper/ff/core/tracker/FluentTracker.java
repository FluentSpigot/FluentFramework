package io.github.jwdeveloper.ff.core.tracker;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class FluentTracker {

    private Map<UUID, TrackedPlayer> players;

    public FluentTracker() {
        this.players = new HashMap<>();
    }


    public TrackedPlayer trackPlayer(Player player) {
        if (players.containsKey(player.getUniqueId())) {
            return players.get(player.getUniqueId());
        }
        var tp = new TrackedPlayer(player.getUniqueId());
        players.put(player.getUniqueId(), tp);
        return players.get(player.getUniqueId());
    }
}
