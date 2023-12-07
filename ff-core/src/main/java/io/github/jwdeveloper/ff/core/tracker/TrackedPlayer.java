package io.github.jwdeveloper.ff.core.tracker;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import lombok.Getter;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;
import java.util.function.Consumer;

@Getter
public class TrackedPlayer
{
    private UUID uuid;
    private boolean online;
    private boolean active;
    private EventGroup<PlayerJoinEvent> joinEvent;
    private EventGroup<PlayerQuitEvent> quiteEvent;
    private EventGroup<PlayerDeathEvent> onDeath;

    public TrackedPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public TrackedPlayer onJoin(Consumer<PlayerJoinEvent> event)
    {

        return this;
    }

    public TrackedPlayer onQuit(Consumer<PlayerQuitEvent> event)
    {
        return this;
    }

    public TrackedPlayer onKilled(Consumer<PlayerDeathEvent> event)
    {

        return this;
    }



}
