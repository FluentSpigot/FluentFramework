package io.github.jwdeveloper.ff.core.spigot.commands.implementation;


import io.github.jwdeveloper.ff.core.common.java.ObjectUtility;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.commands.api.FluentCommandRegistry;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class SimpleCommandManger extends EventBase implements FluentCommandRegistry {
    private final Map<String, SimpleCommand> commands;
    private final SimpleCommandMap commandMap;

    public SimpleCommandManger(Plugin plugin) {
        super(plugin);
        commands = new HashMap<>();
        this.commandMap = getCommandMap();
    }

    @Override
    public void onPluginStop(PluginDisableEvent event) {
        for (var command : commands.values()) {
            unregisterBukkitCommand(command);
        }
        commands.clear();
    }


    public boolean register(SimpleCommand command) {
        if (commands.containsKey(command.getName())) {
            FluentLogger.LOGGER.warning("command already exists", command.getName());
            return false;
        }
        if (!registerBukkitCommand(command)) {
            FluentLogger.LOGGER.warning("unable to register command ", command.getName());
            return false;
        }
        commands.put(command.getName(), command);
        return true;
    }


    public boolean unregister(SimpleCommand command) {
        if (!unregisterBukkitCommand(command)) {
            return false;
        }
        commands.remove(command.getName());
        return true;
    }


    private boolean registerBukkitCommand(SimpleCommand simpleCommand) {
        try {
            var result = commandMap.register(plugin.getName(), simpleCommand);
            updateBukkitCommands();
            return result;
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Unable to register command " + simpleCommand.getName(), e);
            return false;
        }
    }

    private boolean unregisterBukkitCommand(SimpleCommand command) {
        try {
            var field = SimpleCommandMap.class.getDeclaredField("knownCommands");
            field.setAccessible(true);
            var map = field.get(commandMap);
            field.setAccessible(false);
            var knownCommands = (HashMap<String, Command>) map;
            knownCommands.remove(command.getName(), command);
            knownCommands.remove(plugin.getName() + ":" + command.getName(), command);
            command.unregister(commandMap);
            for (String alias : command.getAliases()) {
                if (!knownCommands.containsKey(alias))
                    continue;

                if (!knownCommands.get(alias).toString().contains(command.getName())) {
                    continue;
                }
                knownCommands.remove(alias);
            }
            updateBukkitCommands();
            return true;
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Unable to unregister command " + command.getName(), e);
            return false;
        }
    }

    private void updateBukkitCommands() throws Exception {
        var server = Bukkit.getServer();
        var syncCommand = Arrays.stream(server.getClass().getMethods()).filter(e -> e.getName().equals("syncCommands")).findFirst();
        if (syncCommand.isPresent()) {
            var syncMethod = syncCommand.get();
            syncMethod.invoke(Bukkit.getServer());
        }
    }

    public List<String> getBukkitCommandsNames() {
        return commandMap.getCommands().stream().map(Command::getName).toList();
    }

    public List<Command> getBukkitCommands() {
        return commandMap.getCommands().stream().toList();
    }

    @Override
    public Collection<SimpleCommand> getSimpleCommands() {
        return commands.values();
    }


    private SimpleCommandMap getCommandMap() {
        try {
            return (SimpleCommandMap) ObjectUtility.getPrivateField(Bukkit.getServer(), "commandMap");
        } catch (Exception e) {
            throw new RuntimeException("Unable to get the Simple command map!", e);
        }
    }

}
