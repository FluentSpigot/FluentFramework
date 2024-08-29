package io.github.jwdeveloper.ff.plugin.commands;

import io.github.jwdeveloper.spigot.commands.api.Command;
import io.github.jwdeveloper.spigot.commands.api.Commands;
import io.github.jwdeveloper.spigot.commands.api.argumetns.ArgumentTypes;
import io.github.jwdeveloper.spigot.commands.api.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.api.patterns.Patterns;
import io.github.jwdeveloper.spigot.commands.api.services.EventsService;
import lombok.Getter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CommandsOptions implements Commands {

    private Commands commands;

    @Getter
    private Map<Class, Consumer<CommandBuilder>> map;

    public CommandsOptions(Commands commands) {
        this.commands = commands;
        map = new HashMap<>();
    }


    @Override
    public EventsService globalEvents() {
        return commands.globalEvents();
    }

    @Override
    public Patterns patterns() {
        return commands.patterns();
    }

    @Override
    public ArgumentTypes argumentTypes() {
        return commands.argumentTypes();
    }

    @Override
    public CommandBuilder create(String pattern) {
        return commands.create(pattern);
    }

    @Override
    public CommandBuilder create(Object templateObject) {
        return commands.create(templateObject);
    }

    public void create(Class<?> commandType, Consumer<CommandBuilder> builder) {
        map.put(commandType, builder);
    }

    public void create(Class<?> commandType) {
        create(commandType, (x) -> {
        });
    }

    @Override
    public void register(Command command) {
        commands.register(command);
    }

    @Override
    public void unregister(Command command) {
        commands.unregister(command);
    }

    @Override
    public void removeAll() {
        commands.removeAll();
    }

    @Override
    public List<Command> findAll() {
        return commands.findAll();
    }

    @Override
    public Stream<Command> findBy(Predicate<Command> predicate) {
        return commands.findBy(predicate);
    }

    @Override
    public Optional<Command> findByName(String commandName) {
        return commands.findByName(commandName);
    }
}
