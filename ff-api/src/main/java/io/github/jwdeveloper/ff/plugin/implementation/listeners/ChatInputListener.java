package io.github.jwdeveloper.ff.plugin.implementation.listeners;

import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ChatInputListener extends EventBase
{
    private final Map<UUID, Consumer<AsyncPlayerChatEvent>> consumerMap;
    private final FluentTaskFactory tasks;

    public ChatInputListener(Plugin plugin, FluentTaskFactory taskManager)
    {
        super(plugin);
        consumerMap = new HashMap<>();
        this.tasks = taskManager;
    }

    public void registerPlayer(Player player, Consumer<AsyncPlayerChatEvent> callback)
    {
        consumerMap.put(player.getUniqueId(),callback);
    }

    public void unregisterPlayer(Player player)
    {
        consumerMap.remove(player.getUniqueId());
    }

    @EventHandler
    private void onQuite(PlayerQuitEvent event)
    {
        consumerMap.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    private void onChat(AsyncPlayerChatEvent event)
    {
        var uuid = event.getPlayer().getUniqueId();
        if(!consumerMap.containsKey(uuid))
        {
            return;
        }
        tasks.task(() ->
        {
            consumerMap.get(uuid).accept(event);
        });
    }
}
