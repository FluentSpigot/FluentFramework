package io.github.jwdeveloper.ff.plugin.implementation.extensions.container.player_scope.implementation;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import lombok.Setter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.function.Consumer;

public class FluentPlayerContextListener extends EventBase
{
    private Set<UUID> fluentPlayerMap = new HashSet<>();

    @Setter
    private Consumer<UUID> onPlayerLeave;

    public FluentPlayerContextListener(Plugin plugin) {
        super(plugin);
    }

    public void register(UUID player)
    {
        fluentPlayerMap.add(player);
    }


    @EventHandler
    private void onLeave(PlayerQuitEvent event)
    {
        var id = event.getPlayer().getUniqueId();
        if(!fluentPlayerMap.contains(id))
        {
            return;
        }
        fluentPlayerMap.remove(id);
        if(onPlayerLeave != null)
        {
            onPlayerLeave.accept(id);
        }
    }
}
