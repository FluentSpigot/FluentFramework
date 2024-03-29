package io.github.jwdeveloper.ff.core.spigot.commands.implementation;


import io.github.jwdeveloper.ff.core.common.java.ObjectUtility;
import io.github.jwdeveloper.ff.core.logger.plugin.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.commands.api.FluentCommandManger;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SimpleCommandManger extends EventBase implements FluentCommandManger{
    private final HashMap<String, SimpleCommand> commands;

    public SimpleCommandManger(Plugin plugin) {
        super(plugin);
        commands = new HashMap<>();
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
            var commandMap =  (SimpleCommandMap)ObjectUtility.getPrivateField(Bukkit.getServer(), "commandMap");
            var registerResult =  commandMap.register(plugin.getName(), simpleCommand);


            return registerResult;
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Unable to register command " + simpleCommand.getName(), e);
            return false;
        }
    }

    private boolean unregisterBukkitCommand(SimpleCommand command) {
        try {
            var commandMap = (SimpleCommandMap)  ObjectUtility.getPrivateField(Bukkit.getServer(), "commandMap");
            var field = SimpleCommandMap.class.getDeclaredField("knownCommands");
            field.setAccessible(true);
            var map = field.get(commandMap);
            field.setAccessible(false);
            var knownCommands = (HashMap<String, Command>) map;
            command.unregister(commandMap);
            knownCommands.remove(command.getName(), command);
            knownCommands.remove(plugin.getName() + ":" + command.getName(), command);
            for (String alias : command.getAliases()) {
                if (!knownCommands.containsKey(alias))
                    continue;

                if (!knownCommands.get(alias).toString().contains(command.getName())) {
                    continue;
                }
                knownCommands.remove(alias);
            }
            return true;
        } catch (Exception e) {
            FluentLogger.LOGGER.error("Unable to unregister command " + command.getName(), e);
            return false;
        }
    }


    public List<String> getBukkitCommandsNames() {
        List<String> result = new ArrayList<>();
        try {
            var commandMap = ObjectUtility.getPrivateField(Bukkit.getServer(), "commandMap");
            var simpleCommandMap = (SimpleCommandMap) commandMap;
            return simpleCommandMap.getCommands().stream().map(c -> c.getName()).toList();
        } catch (Exception e) {
            FluentLogger.LOGGER.error("can't get all commands names", e);
        }
        return result;
    }


    public List<Command> getBukkitCommands() {
        try {
            var commandMap = ObjectUtility.getPrivateField(Bukkit.getServer(), "commandMap");
            var simpleCommandMap = (SimpleCommandMap) commandMap;
            return simpleCommandMap.getCommands().stream().toList();
        } catch (Exception e) {
            FluentLogger.LOGGER.error("can't get all commands", e);
        }
        return new ArrayList<>();
    }

    @Override
    public Collection<SimpleCommand> getSimpleCommands() {
        return commands.values();
    }


}
