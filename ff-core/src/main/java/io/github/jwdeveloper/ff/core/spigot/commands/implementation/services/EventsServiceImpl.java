package io.github.jwdeveloper.ff.core.spigot.commands.implementation.services;

import io.github.jwdeveloper.ff.core.spigot.commands.api.services.EventsService;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.events.CommandEvent;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.events.ConsoleCommandEvent;
import io.github.jwdeveloper.ff.core.spigot.commands.implementation.events.PlayerCommandEvent;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventGroup;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class EventsServiceImpl implements EventsService {
    private EventGroup<CommandEvent> onInvokeEvent = new EventGroup<>();
    private EventGroup<PlayerCommandEvent> onPlayerInvokeEvent = new EventGroup<>();
    private EventGroup<ConsoleCommandEvent> onConsoleInvokeEvent = new EventGroup<>();

    @Override
    public boolean invokeEvent(CommandSender sender, String[] allArgs, String[] commandArgs, Object[] values) {
        if (sender instanceof Player) {
            var eventDto = new PlayerCommandEvent(sender, commandArgs, allArgs, values, true);
            onInvokeEvent.invoke(eventDto);
            onPlayerInvokeEvent.invoke(eventDto);
            return eventDto.getResult();
        }
        if (sender instanceof CommandSender) {
            var eventDto = new ConsoleCommandEvent(sender, commandArgs, allArgs, values, true);
            onInvokeEvent.invoke(eventDto);
            onConsoleInvokeEvent.invoke(eventDto);
            return eventDto.getResult();
        }
        return false;
    }

    @Override
    public void onInvoke(Consumer<CommandEvent> event) {
        if (event == null)
            return;
        onInvokeEvent.subscribe(event);
    }

    @Override
    public void onPlayerInvoke(Consumer<PlayerCommandEvent> event) {
        if (event == null)
            return;
        onPlayerInvokeEvent.subscribe(event);
    }

    @Override
    public void onConsoleInvoke(Consumer<ConsoleCommandEvent> event) {
        if (event == null)
            return;
        onConsoleInvokeEvent.subscribe(event);
    }
}
